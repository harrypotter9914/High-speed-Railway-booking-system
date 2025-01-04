public class Platform {
    private String name;
    private String startTime;
    private String endTime;
    private float mileages;

    // 构造函数
    public Platform(String name, String startTime, String endTime, float mileages) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mileages = mileages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public float getMileages() {
        return mileages;
    }

    public void setMileages(float mileages) {
        this.mileages = mileages;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", mileages=" + mileages +
                '}';
    }


}
