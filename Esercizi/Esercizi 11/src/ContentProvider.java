public class ContentProvider {
    private String name;
    private String licenseCode;

    public ContentProvider(String name, String licenseCode){
        this.name = name;
        this.licenseCode = licenseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    public String getInfo(){
        return "Provider: " + getName() + " (Code: " + getLicenseCode() + ")";
    }
}
