prompt |-----------------------------|;
prompt |-----------TASK 4------------|;
prompt |-----------------------------|;

--Creating table "cust" as Heap Organized.
CREATE TABLE cust
  ( cust_nm VARCHAR2(80 CHAR)
  , cust_add VARCHAR2(128 CHAR)
  , CONSTRAINT pk_cust PRIMARY KEY (cust_nm)
  )
  ORGANIZATION HEAP;
  
  --COMMENTS
  COMMENT ON TABLE cust IS 'Table to store details of customer.';
  COMMENT ON COLUMN cust.cust_nm IS 'Average name length of people is 70 CHAR.';
  COMMENT ON COLUMN cust.cust_add IS 'Average length of address is 35 CHAR for each line.';

--Creating table "asmbly" as Index Organized.
CREATE TABLE asmbly
  ( asmbly_id NUMBER(6)
  , ordr_dt DATE NOT NULL
  , asmbly_dtls VARCHAR2(128 CHAR)
  , cust_nm VARCHAR2(80 CHAR) NOT NULL
  , CONSTRAINT pk_asmbly
      PRIMARY KEY (asmbly_id)
  , CONSTRAINT fk_asmbly
      FOREIGN KEY (cust_nm)
      REFERENCES cust(cust_nm)
  )
  ORGANIZATION INDEX;
  
  --COMMENTS
  COMMENT ON TABLE asmbly IS 'Table to store details of assemblies ordered.';
  COMMENT ON COLUMN asmbly.asmbly_id IS '50 Assembly ID are being created per day hence this size would work for approx 6 years.';
  COMMENT ON COLUMN asmbly.ordr_dt IS 'Date should be a necessary field while entering assembly id hence NOT NULL';
  COMMENT ON COLUMN asmbly.asmbly_dtls IS 'It was assumed that the details could be a short description of assembly.';
  COMMENT ON COLUMN asmbly.cust_nm IS 'cust_nm should be necessary field and hence NOT NULL';
  
--Creating table "dept" as Heap Organized.
CREATE TABLE dept
  ( dept_no NUMBER(4)
  , dept_data VARCHAR2(128 CHAR)
  , CONSTRAINT pk_dept PRIMARY KEY (dept_no)
  )
  ORGANIZATION HEAP;
  
  --COMMENTS
  COMMENT ON TABLE dept IS 'Table is to store details of department.';
  COMMENT ON COLUMN dept.dept_no IS 'Departments are added infrequently, hence the size has been kept to 4 digits.';
  COMMENT ON COLUMN dept.dept_data IS 'Assumed that it would be a short description of the deparment.';

--Creating table "proc" as Index Organized.
--index on dept_no is created separately.
CREATE TABLE proc
  ( proc_id NUMBER(4)
  , proc_data VARCHAR2(128 CHAR)
  , dept_no NUMBER(4) NOT NULL
  , CONSTRAINT pk_proc
      PRIMARY KEY (proc_id)
  , CONSTRAINT fk_proc
      FOREIGN KEY (dept_no)
      REFERENCES dept(dept_no)
  )
  ORGANIZATION INDEX;
  
  --COMMENTS
  COMMENT ON TABLE proc IS 'Table to store process details.';
  COMMENT ON COLUMN proc.proc_id IS 'Since number of process ids entered is infrequent, size has been kept to 4 digits';
  COMMENT ON COLUMN proc.proc_data IS 'Assumed brief description of process.';
  COMMENT ON COLUMN proc.dept_no IS 'The supervising department number is stored in this column. Since each process has supervising department, hence not null.';
  
  --INDEXES
  CREATE INDEX ix_proc_dept_no ON proc (dept_no);

--Creating table "fit_proc" as Index Organized.
CREATE TABLE fit_proc
  ( proc_id NUMBER(4)
  , fit_typ VARCHAR2(16 CHAR)
  , CONSTRAINT pk_fit_proc
      PRIMARY KEY (proc_id)
  , CONSTRAINT fk_fit_proc
      FOREIGN KEY (proc_id)
      REFERENCES proc(proc_id)
  )
  ORGANIZATION INDEX;
  
  --COMMENTS
  COMMENT ON TABLE fit_proc IS 'Table to store the process of type fit.';
  COMMENT ON COLUMN fit_proc.proc_id IS 'Process id which references the proc_id from proc table.';
  COMMENT ON COLUMN fit_proc.fit_typ IS 'Assumed type would be stored so would take less characters than description.';

--Creating table "pnt_proc" as Index Organized.
CREATE TABLE pnt_proc
  ( proc_id NUMBER(4)
  , pnt_typ VARCHAR2(16 CHAR)
  , pnt_mthd VARCHAR2(16 CHAR)
  , CONSTRAINT pk_pnt_proc PRIMARY KEY (proc_id)
  , CONSTRAINT fk_pnt_proc FOREIGN KEY (proc_id) REFERENCES proc(proc_id)
  )
  ORGANIZATION INDEX;
  
  --COMMENTS
  COMMENT ON TABLE pnt_proc IS 'Table to store paint process.';
  COMMENT ON COLUMN pnt_proc.proc_id IS 'Process id which references the proc_id from proc table.';
  COMMENT ON COLUMN pnt_proc.pnt_typ IS 'To store paint type.';
  COMMENT ON COLUMN pnt_proc.pnt_mthd IS 'Column to store paint method.';
  
  --INDEXES
  CREATE INDEX ix_pnt_proc_pnt_mthd ON pnt_proc (pnt_mthd);

--Creating table "cut_proc" as Index Organized.
CREATE TABLE cut_proc
  ( proc_id NUMBER(4)
  , cut_typ VARCHAR2(16 CHAR)
  , mchn_typ VARCHAR2(16 CHAR)
  , CONSTRAINT pk_cut_proc
      PRIMARY KEY (proc_id)
  , CONSTRAINT fk_cut_proc
      FOREIGN KEY (proc_id)
      REFERENCES proc(proc_id)
  )
  ORGANIZATION INDEX;
  
  --COMMENTS
  COMMENT ON TABLE CUT_proc IS 'Table to store cut process.';
  COMMENT ON COLUMN cut_proc.proc_id IS 'Process id which references the proc_id from proc table.';
  COMMENT ON COLUMN cut_proc.cut_typ IS 'To store cut type.';
  COMMENT ON COLUMN cut_proc.mchn_typ IS 'Column to store machine type.';
  
--Creating table "jobs" as Index Organized
CREATE TABLE jobs
  ( job_no NUMBER(7)
  , strt_dt DATE NOT NULL
  , end_dt DATE
  , lbr_tim INTERVAL DAY TO SECOND
  , CONSTRAINT pk_jobs PRIMARY KEY (job_no)
  )
  ORGANIZATION INDEX;
  
  --COMMENTS
  COMMENT ON TABLE jobs IS 'Table to store job no and other details.';
  COMMENT ON COLUMN jobs.job_no IS 'Jobs are entered at the rate of 50/day. Keeping it 6 digits would be good enough for less than 5 years. Hence 7 digits.';
  COMMENT ON COLUMN jobs.strt_dt IS 'Start date of the job.';
  COMMENT ON COLUMN jobs.end_dt IS 'End date of the job.';
  COMMENT ON COLUMN jobs.lbr_tim IS 'Time that took to complete the job. Assumbed to be in hours.';
  
  --INDEXES
  CREATE INDEX ix_jobs_end_dt ON jobs (end_dt);

--Creating table 'fit_job' as Index Organized.
CREATE TABLE fit_job
  ( job_no NUMBER(7)
  , CONSTRAINT pk_fit_job
      PRIMARY KEY (job_no)
  , CONSTRAINT fk_fit_job
      FOREIGN KEY (job_no)
      REFERENCES jobs(job_no)
  )
  ORGANIZATION INDEX;
  
  --COMMENTS
  COMMENT ON TABLE fit_job IS 'Table to store fit jobs.';
  COMMENT ON COLUMN fit_job.job_no IS 'This column references job_no in the jobs table.';

--Creating table 'cut_job' as Index Organized.
CREATE TABLE cut_job
  ( job_no NUMBER(7)
  , mchn_typ VARCHAR2(16 CHAR)
  , tim_usd INTERVAL DAY TO SECOND
  , mtrl_usd VARCHAR2(16 CHAR)
  , CONSTRAINT pk_cut_job
      PRIMARY KEY (job_no)
  , CONSTRAINT fk_cut_job
      FOREIGN KEY (job_no)
      REFERENCES jobs(job_no)
  )
  ORGANIZATION INDEX;
  
  --COMMENTS
  COMMENT ON TABLE cut_job IS 'Table to store cut jobs.';
  COMMENT ON COLUMN cut_job.job_no IS 'This column references job_no in the jobs table.';
  COMMENT ON COLUMN cut_job.mchn_typ IS 'This column stores the machine type used in the job. Assumed to be abbreviation.';
  COMMENT ON COLUMN cut_job.tim_usd IS 'Duration of time. Assumed to be in hours.';
  COMMENT ON COLUMN cut_job.mtrl_usd IS 'Stores the material used in the cut job.';
  
--Creating table 'pnt_job' as Index Organized.
CREATE TABLE pnt_job
  ( job_no NUMBER(7)
  , color VARCHAR2(16 CHAR)
  , vol NUMBER(3,2)
  , CONSTRAINT pk_pnt_job
      PRIMARY KEY (job_no)
  , CONSTRAINT fk_pnt_job
      FOREIGN KEY (job_no)
      REFERENCES jobs(job_no)
  )
  ORGANIZATION INDEX;
  
  --COMMENTS
  COMMENT ON TABLE pnt_job IS 'Table to store paint jobs';
  COMMENT ON COLUMN pnt_job.job_no IS 'The job number stored in this column references the job number in the jobs table.';
  COMMENT ON COLUMN pnt_job.color IS 'To store the color of the paint job.';
  COMMENT ON COLUMN pnt_job.vol IS 'To store the volume of the paint used.';
  
  --INDEX
  CREATE INDEX ix_pnt_job_color ON pnt_job (color);

--Creating table "trans" as Heap Organized
CREATE TABLE trans
  ( trans_id NUMBER(7)
  , sup_cost NUMBER(7,2) NOT NULL
  , job_no NUMBER(7) NOT NULL
  , CONSTRAINT pk_trans
      PRIMARY KEY (trans_id)
  , CONSTRAINT fk_trans
      FOREIGN KEY (job_no)
      REFERENCES jobs(job_no)
  )
  ORGANIZATION HEAP;
  --COMMENTS
  COMMENT ON TABLE trans IS 'Table to record the transactions against a job.';
  COMMENT ON COLUMN trans.trans_id IS 'This column stores the transaction. Since 50 transactions are created each day, 7 digits seem appropriate.';
  COMMENT ON COLUMN trans.sup_cost IS 'Stores cost of the transaction. Assumed that the cost of any transaction would not be more than 5 figures.';
  COMMENT ON COLUMN trans.job_no IS 'The job against which the transaction is being recorded. This references job_no in jobs table.';

--Creating table "accnt" as Heap Organized.
CREATE TABLE accnt
  ( accnt_no NUMBER(6)
  , estd_dt DATE NOT NULL
  , CONSTRAINT pk_accnt
      PRIMARY KEY (accnt_no)
  )
  ORGANIZATION HEAP;
  
  --COMMENTS
  COMMENT ON TABLE accnt IS 'This table stores account numbers and their established date.';
  COMMENT ON COLUMN accnt.accnt_no IS 'Column to store account number. 10 accounts are created per day 5 digits would not even last 3 years.';
  COMMENT ON COLUMN accnt.estd_dt IS 'Established date of the account. Should be not null as this is an important attibute.';

--Creating table "asmbly_accnt" as Index Organized
CREATE TABLE asmbly_accnt
  ( accnt_no NUMBER(6)
  , details1 NUMBER(8,2)
  , asmbly_id NUMBER(6) NOT NULL
  , CONSTRAINT pk_asmbly_accnt
      PRIMARY KEY (asmbly_id, accnt_no)
  , CONSTRAINT fk_asmbly_accnt_asmbly_id
      FOREIGN KEY (asmbly_id)
      REFERENCES asmbly(asmbly_id)
  , CONSTRAINT fk_asmbly_accnt_accnt_no
      FOREIGN KEY (accnt_no)
      REFERENCES accnt(accnt_no)
  )
  ORGANIZATION INDEX;
  
  --COMMENTS
  COMMENT ON TABLE asmbly_accnt IS 'Table to store the account, expenditure on the account and the assembly it is associated to.';
  COMMENT ON COLUMN asmbly_accnt.accnt_no IS 'Stores account number which references accnt table.';
  COMMENT ON COLUMN asmbly_accnt.details1 IS 'This column stores the sum of cost of all the transactions against the relevant assembly id.';
  COMMENT ON COLUMN asmbly_accnt.asmbly_id IS 'This column stores the asmbly_id that the account is associated with.';
  
  --INDEX
  CREATE INDEX ix_asmbly_accnt_asmbly_id ON asmbly_accnt (asmbly_id);

--Creating table "proc_accnt" as Index Organized
CREATE TABLE proc_accnt
  ( accnt_no NUMBER(6)
  , details3 NUMBER(8,2)
  , proc_id NUMBER(4) NOT NULL
  , CONSTRAINT pk_proc_accnt
      PRIMARY KEY (proc_id, accnt_no)
  , CONSTRAINT fk_proc_accnt_proc_id
      FOREIGN KEY (proc_id)
      REFERENCES proc(proc_id)
  , CONSTRAINT fk_proc_accnt_accnt_no
      FOREIGN KEY (accnt_no)
      REFERENCES accnt(accnt_no)
  )
  ORGANIZATION INDEX;
  
  --COMMENTS
  COMMENT ON TABLE proc_accnt IS 'Table to store the account, expenditure on the account and the process it is associated to.';
  COMMENT ON COLUMN proc_accnt.accnt_no IS 'Stores account number which references accnt table.';
  COMMENT ON COLUMN proc_accnt.details3 IS 'This column stores the sum of cost of all the transactions against the relevant process id.';
  COMMENT ON COLUMN proc_accnt.proc_id IS 'This column stores the proc_id that the account is associated with.';
  
  --INDEX
  CREATE INDEX ix_proc_accnt_proc_id ON proc_accnt (proc_id);
  
--Creating table "dept_accnt" as Index Organized
CREATE TABLE dept_accnt
  ( accnt_no NUMBER(6)
  , details2 NUMBER(8, 2)
  , dept_no NUMBER(4) NOT NULL
  , CONSTRAINT pk_dept_accnt
      PRIMARY KEY (dept_no, accnt_no)
  , CONSTRAINT fk_dept_accnt_dept_no
      FOREIGN KEY (dept_no)
      REFERENCES dept(dept_no)
  , CONSTRAINT fk_dept_accnt_accnt_no
      FOREIGN KEY (accnt_no)
      REFERENCES accnt(accnt_no)
  )
  ORGANIZATION INDEX;
  
  --COMMENTS
  COMMENT ON TABLE dept_accnt IS 'Table to store the account, expenditure on the account and the department it is associated to.';
  COMMENT ON COLUMN dept_accnt.accnt_no IS 'Stores account number which references accnt table.';
  COMMENT ON COLUMN dept_accnt.details2 IS 'This column stores the sum of cost of all the transactions against the relevant department number.';
  COMMENT ON COLUMN dept_accnt.dept_no IS 'This column stores the dept_no that the account is associated with.';
  
  --INDEX  
  CREATE INDEX ix_dept_accnt_dept_no ON dept_accnt (dept_no);

--Creating table "a_accnt_trans" as Heap Organized.
CREATE TABLE a_accnt_trans
  ( trans_id NUMBER(7)
  , accnt_no NUMBER(6) NOT NULL
  , CONSTRAINT pk_a_accnt_trans
      PRIMARY KEY (trans_id)
  , CONSTRAINT fk_a_accnt_trans_trans_id
      FOREIGN KEY (trans_id)
      REFERENCES trans(trans_id)
  , CONSTRAINT fk_a_accnt_trans_accnt_no
      FOREIGN KEY (accnt_no)
      REFERENCES accnt(accnt_no)
  )
  ORGANIZATION HEAP;
  --COMMENTS
  COMMENT ON TABLE a_accnt_trans IS 'This table stores all the transactions against assembly accounts.';
  COMMENT ON COLUMN a_accnt_trans.trans_id IS 'This is the transaction id and references table trans.';
  COMMENT ON COLUMN a_accnt_trans.accnt_no IS 'This column stores the account against which the transaction is applicable. accnt_no references asbmly_accnt table.';

--Creating table "p_accnt_trans" as Heap Organized.
CREATE TABLE p_accnt_trans
  ( trans_id NUMBER(7)
  , accnt_no NUMBER(6) NOT NULL
  , CONSTRAINT pk_p_accnt_trans
      PRIMARY KEY (trans_id)
  , CONSTRAINT fk_p_accnt_trans_trans_id
      FOREIGN KEY (trans_id)
      REFERENCES trans(trans_id)
  , CONSTRAINT fk_p_accnt_trans_accnt_no
      FOREIGN KEY (accnt_no)
      REFERENCES accnt(accnt_no)
  )
  ORGANIZATION HEAP;
  --COMMENTS
  COMMENT ON TABLE p_accnt_trans IS 'This table stores all the transactions against process accounts.';
  COMMENT ON COLUMN p_accnt_trans.trans_id IS 'This is the transaction id and references table trans.';
  COMMENT ON COLUMN p_accnt_trans.accnt_no IS 'This column stores the account against which the transaction is applicable. accnt_no references asbmly_accnt table.';

--Creating table "d_accnt_trans" as Heap Organized.
CREATE TABLE d_accnt_trans
  ( trans_id NUMBER(7)
  , accnt_no NUMBER(6) NOT NULL
  , CONSTRAINT pk_d_accnt_trans
      PRIMARY KEY (trans_id)
  , CONSTRAINT fk_d_accnt_trans_trans_id
      FOREIGN KEY (trans_id)
      REFERENCES trans(trans_id)
  , CONSTRAINT fk_d_accnt_trans_accnt_no
      FOREIGN KEY (accnt_no)
      REFERENCES accnt(accnt_no)
  )
  ORGANIZATION HEAP;
  --COMMENTS
  COMMENT ON TABLE d_accnt_trans IS 'This table stores all the transactions against department accounts.';
  COMMENT ON COLUMN d_accnt_trans.trans_id IS 'This is the transaction id and references table trans.';
  COMMENT ON COLUMN d_accnt_trans.accnt_no IS 'This column stores the account against which the transaction is applicable. accnt_no references asbmly_accnt table.';

--Creating table "job_assgn" as Index Organized
CREATE TABLE job_assgn
  ( job_no NUMBER(7)
  , asmbly_id NUMBER(6) NOT NULL
  , proc_id NUMBER(4) NOT NULL
  , CONSTRAINT pk_job_assgn
      PRIMARY KEY (job_no)
  , CONSTRAINT fk_job_assgn_job_no
      FOREIGN KEY (job_no)
      REFERENCES jobs(job_no)
  , CONSTRAINT fk_job_assgn_asmbly_id
      FOREIGN KEY (asmbly_id)
      REFERENCES asmbly(asmbly_id)
  , CONSTRAINT fk_job_assgn_proc_id
      FOREIGN KEY (proc_id)
      REFERENCES proc(proc_id)
  )
  ORGANIZATION INDEX;
  
  --COMMENTS
  COMMENT ON TABLE job_assgn IS 'This tables stores the mapping of the job number, assembly id and the process id.';
  COMMENT ON COLUMN job_assgn.job_no IS 'This is the job number assigned when process assigned to any assembly starts.';
  COMMENT ON COLUMN job_assgn.asmbly_id IS 'The assembly on which the job is being executed.';
  COMMENT ON COLUMN job_assgn.proc_id IS 'This is the process id that is assigned to the assembly.';
  
  --INDEXES
  CREATE INDEX ix_job_assgn_asmbly_id ON job_assgn (asmbly_id);
  CREATE INDEX ix_job_assgn_proc_id ON job_assgn (proc_id);

/*DROPPING ORDER
DROP TABLE ACCNT_TRANS;
DROP TABLE JOB_ASSGN;
DROP TABLE TRANS;
DROP TABLE PROC_ACCNT;
DROP TABLE DEPT_ACCNT;
DROP TABLE ASMBLY_ACCNT;
DROP TABLE ACCNT;
DROP TABLE FIT_JOB;
DROP TABLE CUT_JOB;
DROP TABLE PNT_JOB;
DROP TABLE JOBS;
DROP TABLE ASMBLY;
DROP TABLE CUST;
DROP TABLE FIT_PROC;
DROP TABLE PNT_PROC;
DROP TABLE CUT_PROC;
DROP TABLE PROC;
DROP TABLE DEPT;
*/