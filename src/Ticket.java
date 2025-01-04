public class Ticket {
    private String id;
    private String date;
    private String carID;
    private String startStation;
    private String endStation;
    private String paperType;
    private String idCard;
    private String name;
    private String startTime;
    private String endTime;
    private String seatType;
    private int seatNo;
    private float price;

    // 构造函数
    public Ticket(String id, String date, String carID, String startStation, String endStation,
                  String paperType, String idCard, String name, String startTime,
                  String endTime, String seatType, int seatNo, float price) {
        this.id = id;
        this.date = date;
        this.carID = carID;
        this.startStation = startStation;
        this.endStation = endStation;
        this.paperType = paperType;
        this.idCard = idCard;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seatType = seatType;
        this.seatNo = seatNo;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
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

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
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

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isPriceValid() {
        return price >= 0;
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", carID='" + carID + '\'' +
                ", startStation='" + startStation + '\'' +
                ", endStation='" + endStation + '\'' +
                ", paperType='" + paperType + '\'' +
                ", idCard='" + idCard + '\'' +
                ", name='" + name + '\'' +
                ", seatType='" + seatType + '\'' +
                ", seatNo=" + seatNo +
                ", price=" + price +
                '}';
    }

}
