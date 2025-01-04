public class Passenger {
    private String idCard;
    private String name;
    private String paperType;
    private String selfPhone;
    private String emergencyContacter;
    private String emergencyContacterPhone;

    // 构造函数
    public Passenger(String idCard, String name, String paperType, String selfPhone,
                     String emergencyContacter, String emergencyContacterPhone) {
        this.idCard = idCard;
        this.name = name;
        this.paperType = paperType;
        this.selfPhone = selfPhone;
        this.emergencyContacter = emergencyContacter;
        this.emergencyContacterPhone = emergencyContacterPhone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public String getSelfPhone() {
        return selfPhone;
    }

    public void setSelfPhone(String selfPhone) {
        this.selfPhone = selfPhone;
    }

    public String getEmergencyContacter() {
        return emergencyContacter;
    }

    public void setEmergencyContacter(String emergencyContacter) {
        this.emergencyContacter = emergencyContacter;
    }

    public String getEmergencyContacterPhone() {
        return emergencyContacterPhone;
    }

    public void setEmergencyContacterPhone(String emergencyContacterPhone) {
        this.emergencyContacterPhone = emergencyContacterPhone;
    }

    @Override
    public String toString() {
        return name + " - ID: " + idCard + " - Type: " + paperType;
    }

}
