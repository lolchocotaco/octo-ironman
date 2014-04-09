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

    @Override
    public String toString(){
        return this.str;
    }
}
