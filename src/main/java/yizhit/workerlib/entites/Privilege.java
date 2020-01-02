package yizhit.workerlib.entites;

import entity.query.Queryable;
import entity.query.annotation.DataSource;

import java.io.Serializable;
import java.util.UUID;

@DataSource("workerlib2")
public class Privilege extends Queryable<Privilege> implements Serializable {
    private String privilegeId;     //权限表ID
    private String groupId;         //分组表id
    private UUID roleId;          //角色id
    private String aclId;
    private int canAdd;
    private int canDelete;
    private int canUpdate;
    private int canView;
    private int canDownload;
    private int canPreviewDoc;
    private int canUpload;
    private int canExport;
    private int canImport;
    private int canDecrypt;
    private int canList;
    private int canQuery;
    private int scope;
    private String userPath;
    private int modifyTime;
    private int modifyBy;
    private String createOn;
    private int createBy;


    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getAclId() {
        return aclId;
    }

    public void setAclId(String aclId) {
        this.aclId = aclId;
    }

    public int getCanAdd() {
        return canAdd;
    }

    public void setCanAdd(int canAdd) {
        this.canAdd = canAdd;
    }

    public int getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(int canDelete) {
        this.canDelete = canDelete;
    }

    public int getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(int canUpdate) {
        this.canUpdate = canUpdate;
    }

    public int getCanView() {
        return canView;
    }

    public void setCanView(int canView) {
        this.canView = canView;
    }

    public int getCanDownload() {
        return canDownload;
    }

    public void setCanDownload(int canDownload) {
        this.canDownload = canDownload;
    }

    public int getCanPreviewDoc() {
        return canPreviewDoc;
    }

    public void setCanPreviewDoc(int canPreviewDoc) {
        this.canPreviewDoc = canPreviewDoc;
    }

    public int getCanUpload() {
        return canUpload;
    }

    public void setCanUpload(int canUpload) {
        this.canUpload = canUpload;
    }

    public int getCanExport() {
        return canExport;
    }

    public void setCanExport(int canExport) {
        this.canExport = canExport;
    }

    public int getCanImport() {
        return canImport;
    }

    public void setCanImport(int canImport) {
        this.canImport = canImport;
    }

    public int getCanDecrypt() {
        return canDecrypt;
    }

    public void setCanDecrypt(int canDecrypt) {
        this.canDecrypt = canDecrypt;
    }

    public int getCanList() {
        return canList;
    }

    public void setCanList(int canList) {
        this.canList = canList;
    }

    public int getCanQuery() {
        return canQuery;
    }

    public void setCanQuery(int canQuery) {
        this.canQuery = canQuery;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public String getUserPath() {
        return userPath;
    }

    public void setUserPath(String userPath) {
        this.userPath = userPath;
    }

    public int getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(int modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(int modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getCreateOn() {
        return createOn;
    }

    public void setCreateOn(String createOn) {
        this.createOn = createOn;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }



}
