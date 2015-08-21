package ro.teamnet.ou.web.rest.dto;

public class FunctionRelationshipDTO {

    private Long id;
    private Long functionId;
    private String code;
    private AccountDTO account;
    private OrganizationalUnitDTO organizationalUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public OrganizationalUnitDTO getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(OrganizationalUnitDTO organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }
}
