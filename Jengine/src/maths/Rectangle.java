package maths;

import java.io.Serializable;

public class Rectangle implements Serializable{

	public Vector2d position;
	public double width,height;
	public int xoffset = 0;
	public int yoffset = 0;
	
	public Rectangle(double x,double y , double width, double height){
		this.position = new Vector2d(x, y);
		this.width = width;
		this.height = height;
	}
	
	public Rectangle(Vector2d position,double width, double height){
		this.position = position;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle(Vector2d position,double width, double height, int offset){
		this(position,width,height);
		this.xoffset = offset;
		this.yoffset = offset;
	}
	
	public Rectangle(double x,double y , double width, double height, int xoffset, int yoffset){
		this(new Vector2d(x, y),width,height,xoffset,yoffset);
	}
	
	public Rectangle(Vector2d position,double width, double height, int xoffset, int yoffset){
		this(position,width,height);
		this.xoffset = xoffset;
		this.yoffset = yoffset;
	}
	
	 public boolean contains(double x, double y, double width, double height) {

		 for(int i = 0; i < width ;i++){
			 if(contains(x+i,y+1))
				 return true;
		 }
		 for(int i = 0; i < width ;i++){
			 if(contains(x+i,y+height-1))
				 return true;
		 }
		 for(int i = 0; i < height ;i++){
			 if(contains(x+1,y+i))
				 return true;
		 }
		 for(int i = 0; i < height ;i++){
			 if(contains(x+width-1,y+i))
				 return true;
		 }
		 /*
         if(contains(x,y)){
             return true;
         }else if(contains(x+width,y)){
             return true;
         }else if(contains(x,y+height)){
             return true;
         }else if(contains(x+width,y+height)){
             return true;
         }*/

         /*if(x>this.position.x-xoffset && x+width < this.position.x+this.width-xoffset){
             if(y>this.position.y-yoffset && y+height < this.position.y+this.height-yoffset){
                 return true;
             }
         }*/

		 /*for(int xx = (int)x;xx < x+width;xx++){
			 for(int yy = (int)y;yy < y+height;yy++){
				 if(contains(xx, yy))return true;
			 }
		 }*/
		 
		 return false;
        
	}

	public boolean contains(double x, double y){
		if(x >= (position.x-xoffset) && x <= (position.x+width-xoffset)){
			if(y >= (position.y-yoffset) && y <= (position.y+height-yoffset)){
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(Rectangle r){
		boolean result = contains((int)r.position.x-r.xoffset , (int)r.position.y-r.yoffset, (int)r.width, (int)r.height);
		return result;
	}

	public Rectangle add(Rectangle other){
        if(other.position.x < this.position.x ){
            this.width+= this.position.x - other.position.x;
            this.position.x = other.position.x;
        }
        if(other.position.y < this.position.y){
            this.height+= this.position.y - other.position.y;
            this.position.y = other.position.y;
        }
        if((other.position.x + other.width) > (this.position.x + this.width)){
            this.width += (other.position.x + other.width) - (this.position.x + this.width);
        }

        if((other.position.y + other.height) > (this.position.y + this.height)){
            this.height += (other.position.y + other.height) - (this.position.y + this.height);
        }


        return this;
    }
	
}
