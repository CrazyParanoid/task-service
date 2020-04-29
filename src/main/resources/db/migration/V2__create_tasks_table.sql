CREATE TABLE TASKS(
  ID                          bigserial PRIMARY KEY     NOT NULL ,
  TASK_ID                     VARCHAR (200)             NOT NULL ,
  ASSIGNEE_ID                 VARCHAR (200)             NULL ,
  SPRINT_ID                   VARCHAR (200)             NULL ,
  TRACKER_ID                  VARCHAR (200)             NOT NULL ,
  START_DATE                  DATE                      NULL ,
  END_DATE                    DATE                      NULL ,
  WORK_HOURS                  BIGINT                    NULL ,
  PRIORITY                    VARCHAR (100)             NOT NULL ,
  WORK_FLOW_STATUS            VARCHAR (100)             NOT NULL ,
  SUMMARY                     TEXT                      NOT NULL ,
  DESCRIPTION                 TEXT                      NOT NULL
);

CREATE INDEX TASKS_ID_INDEX
  ON TASKS(ID);
