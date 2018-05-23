package schedulingApplication.Models;

public class City {
    private int cityId;
    private String city;
    private int countryId;
    private String createDate;
    private String createBy;
    private String lastUpdate;
    private String lastUpdateBy;

    public City(int cityId, String city, int countryId, String createDate, String createBy, String lastUpdate, String lastUpdateBy) {
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCity() {
        return city;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
}
