--QUERY NUMBER 9
SELECT details1 AS "Cost Incurred"
  FROM asmbly_accnt
  WHERE asmbly_id = 100002;

--QUERY NUMBER 10
SELECT SUM(EXTRACT(HOUR FROM lbr_tim))||' Hrs' AS "Labours Hours"
  FROM jobs
  WHERE job_no IN
  (
    SELECT job_no
      FROM job_assgn
      WHERE asmbly_id = 100002
  );

--QUERY NUMBER 11
SELECT SUM(EXTRACT(HOUR FROM lbr_tim))||' Hrs' AS "Labours Hours"
  FROM jobs
  WHERE job_no IN 
    (
      SELECT job_no 
        FROM job_assgn
        WHERE proc_id IN
      (
        SELECT proc_id
          FROM proc
          WHERE dept_no = 1001
      )
    )
  AND strt_dt >= TO_DATE('10/02/2015','MM/DD/YYYY')
  AND end_dt <= TO_DATE('10/02/2015','MM/DD/YYYY')
  ;

--QUERY 12
SELECT p.proc_id AS "PROCESS ID", p.dept_no AS "SUPERVISING DEPT"
FROM (SELECT job_no, asmbly_id, proc_id
        FROM job_assgn
        WHERE asmbly_id = 100002) ja
INNER JOIN jobs jo
  ON ja.job_no = jo.job_no
INNER JOIN proc p
  ON ja.proc_id = p.proc_id
ORDER BY jo.strt_dt, p.proc_id
;

--QUERY 13
SELECT j.job_no AS "JOB NUMBER", 'FIT' AS "JOB TYPE", ja.asmbly_id AS "ASSEMBLY ID"
  FROM jobs j
INNER JOIN job_assgn ja
  ON j.job_no = ja.job_no
INNER JOIN fit_job fj
  ON j.job_no = fj.job_no
WHERE strt_dt >= TO_DATE('10/03/2015', 'MM/DD/YYYY')
  AND end_dt <= TO_DATE('10/03/2015', 'MM/DD/YYYY')
  AND ja.proc_id IN
  (
    SELECT proc_id
      FROM proc
      WHERE dept_no=1001
  )
UNION
SELECT j.job_no AS "JOB NUMBER", 'PAINT' AS "JOB TYPE", ja.asmbly_id AS "ASSEMBLY ID"
  FROM jobs j
INNER JOIN job_assgn ja
  ON j.job_no = ja.job_no
INNER JOIN pnt_job pj
  ON j.job_no = pj.job_no
WHERE strt_dt >= TO_DATE('10/02/2015', 'MM/DD/YYYY')
  AND end_dt <= TO_DATE('10/02/2015', 'MM/DD/YYYY')
  AND ja.proc_id IN
  (
    SELECT proc_id
      FROM proc
      WHERE dept_no=1001
  )
UNION
SELECT j.job_no AS "JOB NUMBER", 'CUT' AS "JOB TYPE", ja.asmbly_id AS "ASSEMBLY ID"
  FROM jobs j
INNER JOIN job_assgn ja
  ON j.job_no = ja.job_no
INNER JOIN cut_job cj
  ON j.job_no = cj.job_no
WHERE strt_dt >= TO_DATE('10/03/2015', 'MM/DD/YYYY')
  AND end_dt <= TO_DATE('10/03/2015', 'MM/DD/YYYY')
  AND ja.proc_id IN
  (
    SELECT proc_id
      FROM proc
      WHERE dept_no=1002
  );

--QUERY NUMBER 14
SELECT cust_nm as "CUSTOMER NAME"
  FROM asmbly
  WHERE asmbly_id IN
  (
    SELECT asmbly_id
      FROM job_assgn
      WHERE job_no IN
      (
        SELECT job_no
          FROM pnt_job
          WHERE color='RED'
      )
      AND proc_id IN
      (
        SELECT proc_id
          FROM pnt_proc
          WHERE pnt_mthd='PAINT METHOD 1'
      )
  )
  ORDER BY cust_nm;

--QUERY NUMBER 15
DELETE FROM cut_job
  WHERE job_no BETWEEN 1000001 AND 1000011;

--QUERY NUMBER 16
UPDATE pnt_job
  SET color = 'RED'
  WHERE job_no = 1000006;

--QUERY NUMBER 17
SELECT TO_CHAR(AVG(expense),'999,999.99') AS "AVERAGE COST"
  FROM
  (
    SELECT details1 AS expense
      FROM asmbly_accnt
    UNION ALL
    SELECT details3 AS expense
      FROM proc_accnt
    UNION ALL
    SELECT details2 AS expense
      FROM dept_accnt
  );