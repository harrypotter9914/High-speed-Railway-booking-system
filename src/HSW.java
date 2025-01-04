public class HSW {
    private String id;
    private String startStation;
    private String endStation;
    private float mileages;
    private String status;
    private String startTime;
    private String endTime;
    private int platforms;
    private int businessBlocks;
    private int firstSeats;
    private int secondSeats;
    private Platform[] platformList; // Platform 类的数组

    // 构造函数
    public HSW(String id, String startStation, String endStation, float mileages, String status,
               String startTime, String endTime, int platforms, int businessBlocks,
               int firstSeats, int secondSeats, Platform[] platformList) {
        this.id = id;
        this.startStation = startStation;
        this.endStation = endStation;
        this.mileages = mileages;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.platforms = platforms;
        this.businessBlocks = businessBlocks;
        this.firstSeats = firstSeats;
        this.secondSeats = secondSeats;
        this.platformList = platformList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public float getMileages() {
        return mileages;
    }

    public void setMileages(float mileages) {
        this.mileages = mileages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPlatforms() {
        return platforms;
    }

    public void setPlatforms(int platforms) {
        this.platforms = platforms;
    }

    public int getBusinessBlocks() {
        return businessBlocks;
    }

    public void setBusinessBlocks(int businessBlocks) {
        this.businessBlocks = businessBlocks;
    }

    public int getFirstSeats() {
        return firstSeats;
    }

    public void setFirstSeats(int firstSeats) {
        this.firstSeats = firstSeats;
    }

    public int getSecondSeats() {
        return secondSeats;
    }

    public void setSecondSeats(int secondSeats) {
        this.secondSeats = secondSeats;
    }

    public Platform[] getPlatformList() {
        return platformList;
    }

    public void setPlatformList(Platform[] platformList) {
        this.platformList = platformList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HSW hsw = (HSW) o;
        return id != null ? id.equals(hsw.id) : hsw.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}
