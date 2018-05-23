package schedulingApplication.Models;

public class Country {
    private int countryId;
    private String country;
    private String createdDate;
    private String lastUpdate;
    private String lastUpdateBy;

    public Country(int countryId, String country, String createdDate, String lastUpdate, String lastUpdateBy) {
        this.countryId = countryId;
        this.country = country;
        this.createdDate = createdDate;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountry() {
        return country;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
}
