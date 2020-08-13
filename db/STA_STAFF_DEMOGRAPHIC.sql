CREATE TABLE STA_STAFF_DEMOGRAPHIC
(
    STAFF_ID            VARCHAR(16) NOT NULL,
    NATIONALITY         VARCHAR(6),
    ID_CARD_NO          VARCHAR(32) NOT NULL,
    DOMICILE_TYPE       CHAR(1)     NOT NULL, /*户口类型*/
    DOMICILE_PROVINCE   INT(11), /*户口所在地：省级行政单位或直辖市，自治区*/
    DOMICILE_PREFECTURE INT(11), /*地级行政单位*/
    DOMICILE_COUNTY     INT(11), /*县级，区行政单位*/
    DOMICILE_TOWN       INT(11), /*乡镇，街道行政单位*/
    DOMICILE_DETAIL     VARCHAR(100), /*详细地址*/
    CURRENT_PROVINCE    INT(11), /*现居地：省级行政单位或直辖市，自治区*/
    CURRENT_PREFECTURE  INT(11), /*地级行政单位*/
    CURRENT_COUNTY      INT(11), /*县级，区行政单位*/
    CURRENT_TOWN        INT(11), /*乡镇，街道行政单位*/
    CURRENT_DETAIL      VARCHAR(100), /*详细地址*/
    NATIVE_PLACE        INT(11), /*籍贯*/
    NATION              VARCHAR(6), /*民族*/
    RELIGION            VARCHAR(6), /*宗教信仰*/
    IS_ETHNIC_MINORITY  CHAR(1),
    MARITAL_STATUS      CHAR(1),
    CREATED_AT          DATETIME    NOT NULL,
    CREATED_BY          VARCHAR(32) NOT NULL,
    UPDATED_AT          DATETIME    NOT NULL,
    UPDATED_BY          VARCHAR(32) NOT NULL
);