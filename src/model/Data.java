package model;

public class Data
{
    private String[] value;

    public Data(String[] value)
    {
        this.value = value;
    }

    public String[] getValue()
    {
        return value;
    }

    public void getValue(String[] data)
    {
        this.value = data;
    }
}
