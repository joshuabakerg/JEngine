package maths;

import java.io.Serializable;

public class Vector2d implements Serializable {
public double x,y;
	
	public Vector2d(double x , double y){
		this.x = x;
		this.y = y;
	}

	public Vector2d(Vector2d other){
		this.x = other.x;
		this.y = other.y;
	}

	public Vector2d add(Vector2d other){
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	
	public Vector2d subtract(Vector2d other){
		Vector2d result = new Vector2d(this.x,this.y);
		result.x -= other.x;
		result.y -= other.y;
		return result;
	}
	
	public Vector2d divide(Vector2d other){
		this.x /= other.x;
		this.y /= other.y;
		return this;
	}
	
	public void identity(){
		this.x = 0;
		this.y = 0;
	}

    public static Vector2d lerp(Vector2d from , Vector2d to, float f){
        Vector2d result = new Vector2d(0,0);
        result.x = from.x + f * (to.x - from.x);
        result.y = from.y + f * (to.y - from.y);
        return result;
    }

    public Vector2d normalize(){
        double dist = Math.sqrt((this.x*this.x)+(this.y*this.y));
        if(dist == 0)return this;
        this.x = this.x/dist;
        this.y = this.y/dist;
        return this;
    }

    public double distance(Vector2d other){
        double dist = Math.sqrt(Math.pow(other.x-this.x,2)+Math.pow(other.y-this.y,2));
        return dist;
    }
	
	public Vector2d multiply(Vector2d other){
		this.x *= other.x;
		this.y *= other.y;
		return this;
	}
}
