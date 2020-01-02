package yizhit.workerlib.entites;


import entity.query.Queryable;
import entity.query.annotation.DataSource;
import entity.query.annotation.Fieldname;
import entity.query.annotation.PrimaryKey;
import entity.query.annotation.Tablename;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.UUID;

@DataSource("workerlib2")
@Tablename("UserGroupRole")
public class UserGroupRoleModel extends Queryable<UserGroupRoleModel> {

    private static final Logger log = LogManager.getLogger( ccait.ccweb.model.UserGroupRoleModel.class );

    @PrimaryKey
    @Fieldname("userGroupRoleId")
    private String userGroupRoleId;

    @Fieldname("userId")
    private Long userId;

    @Fieldname("groupId")
    private UUID groupId;

    @Fieldname("roleId")
    private UUID roleId;

    @Fieldname("${entity.table.reservedField.userPath:userPath}")
    private String path;

    @Fieldname("${entity.table.reservedField.createOn:createOn}")
    private Date createOn;

    @Fieldname("${entity.table.reservedField.createBy:createBy}")
    private Long createBy;

    @Fieldname("${entity.table.reservedField.modifyOn:modifyOn}")
    private Date modifyOn;

    @Fieldname("${entity.table.reservedField.modifyBy:modifyBy}")
    private Long modifyBy;

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getModifyOn() {
        return modifyOn;
    }

    public void setModifyOn(Date modifyOn) {
        this.modifyOn = modifyOn;
    }

    public Long getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Long modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getUserGroupRoleId() {
        return userGroupRoleId;
    }

    public void setUserGroupRoleId(String userGroupRoleId) {
        this.userGroupRoleId = userGroupRoleId;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
