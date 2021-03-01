package Objetcs;

public class PartsOverviewArticleObject {
    private String OrderNo;
    private String Description;
    private String MatSource;
    private String ItemNo;
    private String ConumserOrder;
    private String Quantity;
    private final String Slack = "0";
    private String ItemDesc;
    private String Storenote;


    public PartsOverviewArticleObject(String orderNo, String description, String matSource, String itemNo, String conumserOrder, String quantity, String itemDesc, String storenote) {
        OrderNo = orderNo;
        Description = description;
        MatSource = matSource;
        ItemNo = itemNo;
        ConumserOrder = conumserOrder;
        Quantity = quantity;
        ItemDesc = itemDesc;
        Storenote = storenote;
    }


    public String getOrderNo() {
        return OrderNo;
    }

    public String getDescription() {
        return Description;
    }

    public String getMatSource() {
        return MatSource;
    }

    public String getItemNo() {
        return ItemNo;
    }

    public String getConumserOrder() {
        return ConumserOrder;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getSlack() {
        return Slack;
    }

    public String getItemDesc() {
        return ItemDesc;
    }

    public String getStorenote() {
        return Storenote;
    }


    @Override
    public String toString() {
        return "PartsOverviewArticleObject{" +
                "OrderNo='" + OrderNo + '\'' +
                ", Description='" + Description + '\'' +
                ", MatSource='" + MatSource + '\'' +
                ", ItemNo='" + ItemNo + '\'' +
                ", ConumserOrder='" + ConumserOrder + '\'' +
                ", Quantity='" + Quantity + '\'' +
                ", Slack='" + Slack + '\'' +
                ", ItemDesc='" + ItemDesc + '\'' +
                ", Storenote='" + Storenote + '\'' +
                '}';
    }
}

