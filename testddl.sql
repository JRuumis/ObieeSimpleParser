CREATE TABLE LogicalTable_RefLogicalColumn (
	logicalColumnRef VARCHAR(120) NULL,
	node_text NUMBER(2,0) NULL,
	refId VARCHAR(80) NULL,
	parent_label VARCHAR(10) NULL,
	parent_mdsid VARCHAR(10) NULL,
	root_label VARCHAR(20) NULL,
	root_mdsid VARCHAR(40) NULL
);
------------------------------------------
CREATE TABLE LogicalTable_Description (
	node_text VARCHAR(410) NULL,
	parent_label VARCHAR(20) NULL,
	parent_mdsid VARCHAR(40) NULL,
	root_label VARCHAR(20) NULL,
	root_mdsid VARCHAR(40) NULL
);
------------------------------------------
CREATE TABLE LogicalTable_RefTableSources (
	node_text NUMBER(2,0) NULL,
	parent_label VARCHAR(20) NULL,
	parent_mdsid VARCHAR(40) NULL,
	root_label VARCHAR(20) NULL,
	root_mdsid VARCHAR(40) NULL
);
------------------------------------------
CREATE TABLE LogicalTable_ExprText (
	node_text VARCHAR(50) NULL,
	parent_label VARCHAR(20) NULL,
	parent_mdsid VARCHAR(40) NULL,
	root_label VARCHAR(20) NULL,
	root_mdsid VARCHAR(40) NULL
);
------------------------------------------
CREATE TABLE LogicalTable_LogicalColumn (
	mdsid VARCHAR(40) NULL,
	iconIndex NUMBER(7,0) NULL,
	isDerived VARCHAR(10) NULL,
	isWriteable VARCHAR(10) NULL,
	name VARCHAR(50) NULL,
	node_text NUMBER(2,0) NULL,
	parent_label VARCHAR(20) NULL,
	parent_mdsid VARCHAR(40) NULL,
	root_label VARCHAR(20) NULL,
	root_mdsid VARCHAR(40) NULL
);
------------------------------------------
CREATE TABLE LogicalTable_RefObject (
	node_text NUMBER(2,0) NULL,
	objectRef VARCHAR(120) NULL,
	objectTypeId NUMBER(6,0) NULL,
	parent_mdsid VARCHAR(10) NULL,
	refId VARCHAR(80) NULL,
	parent_label VARCHAR(20) NULL,
	root_label VARCHAR(20) NULL,
	root_mdsid VARCHAR(40) NULL
);
------------------------------------------
CREATE TABLE LogicalTable_Expr (
	mdsid VARCHAR(40) NULL,
	name VARCHAR(10) NULL,
	node_text NUMBER(2,0) NULL,
	parent_label VARCHAR(20) NULL,
	parent_mdsid VARCHAR(40) NULL,
	root_label VARCHAR(20) NULL,
	root_mdsid VARCHAR(40) NULL
);
------------------------------------------
CREATE TABLE LogicalTable_RefColumns (
	node_text NUMBER(2,0) NULL,
	parent_label VARCHAR(10) NULL,
	parent_mdsid VARCHAR(40) NULL,
	root_label VARCHAR(20) NULL,
	root_mdsid VARCHAR(40) NULL
);
------------------------------------------
CREATE TABLE LogicalTable_ExprTextDesc (
	node_text VARCHAR(120) NULL,
	parent_label VARCHAR(20) NULL,
	parent_mdsid VARCHAR(40) NULL,
	root_label VARCHAR(20) NULL,
	root_mdsid VARCHAR(40) NULL
);
------------------------------------------
CREATE TABLE LogicalTable_RefLogicalTableSource (
	logicalTableSourceRef VARCHAR(120) NULL,
	node_text NUMBER(2,0) NULL,
	refId VARCHAR(80) NULL,
	parent_label VARCHAR(20) NULL,
	parent_mdsid VARCHAR(10) NULL,
	root_label VARCHAR(20) NULL,
	root_mdsid VARCHAR(40) NULL
);
------------------------------------------
CREATE TABLE LogicalTable_AttributeDefn (
	mdsid VARCHAR(40) NULL,
	name VARCHAR(20) NULL,
	node_text NUMBER(2,0) NULL,
	parent_label VARCHAR(20) NULL,
	parent_mdsid VARCHAR(40) NULL,
	root_label VARCHAR(20) NULL,
	root_mdsid VARCHAR(40) NULL
);
------------------------------------------
CREATE TABLE LogicalTable_LogicalKey (
	mdsid VARCHAR(40) NULL,
	isPrimary VARCHAR(10) NULL,
	name VARCHAR(20) NULL,
	node_text NUMBER(2,0) NULL,
	parent_label VARCHAR(20) NULL,
	parent_mdsid VARCHAR(40) NULL,
	root_label VARCHAR(20) NULL,
	root_mdsid VARCHAR(40) NULL
);
------------------------------------------
CREATE TABLE LogicalTable (
	mdsid VARCHAR(40) NULL,
	name VARCHAR(10) NULL,
	node_text NUMBER(2,0) NULL,
	subjectAreaRef VARCHAR(120) NULL,
	y NUMBER(5,0) NULL,
	parent_label VARCHAR(10) NULL,
	parent_mdsid VARCHAR(10) NULL,
	root_mdsid VARCHAR(10) NULL,
	x NUMBER(6,0) NULL,
	root_label VARCHAR(10) NULL
);
------------------------------------------
CREATE TABLE LogicalTable_ObjectRefList (
	node_text NUMBER(2,0) NULL,
	parent_label VARCHAR(20) NULL,
	parent_mdsid VARCHAR(40) NULL,
	root_label VARCHAR(20) NULL,
	root_mdsid VARCHAR(40) NULL
);