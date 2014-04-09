package DistributedMining;

import java.io.Serializable;

public class StringContainer implements Serializable {



    private String str;

	public StringContainer(String str)
	{
		this.str = str;
	}
	
	public String GetString()
	{
		return str;
	}

    public void setString(String str) {
        this.str = str;
    }

    @Override
    public String toString(){
        return this.str;
    }
}
