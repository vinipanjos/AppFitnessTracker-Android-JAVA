package co.tiagoaguiar.codelab.myapplication;

public class MainItem {

    private int id;
    private int textStringId;
    private int drawableId;
    private int color;

    public MainItem(int id, int drawableId, int textStringId, int color) {
        this.id = id;
        this.textStringId = textStringId;
        this.drawableId = drawableId;
        this.color = color;
    }

    public int getTextStringId() {
        return textStringId;
    }

    public void setTextStringId(int textStringId) {
        this.textStringId = textStringId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
}
