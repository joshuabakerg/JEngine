package maths;

public class Rectangle2 {

	public Vector2d position;
	public Vector2d position2;
	//private double width,height;
	public int xoffset = 0;
	public int yoffset = 0;

	public Rectangle2(double x, double y , double width, double height){
		this.position = new Vector2d(x, y);
		this.position2 = new Vector2d(x+width,y+height);
	}

	public Rectangle2(Vector2d position, double width, double height){
		this.position = position;
		this.position2 = new Vector2d(position.x+width,position.y+height);
	}

	public Rectangle2(Vector2d position, double width, double height, int offset){
		this(position,width,height);
		this.xoffset = offset;
		this.yoffset = offset;
	}

	public Rectangle2(double x, double y , double width, double height, int xoffset, int yoffset){
		this(new Vector2d(x, y),width,height,xoffset,yoffset);
	}

	public Rectangle2(Vector2d position, double width, double height, int xoffset, int yoffset){
		this(position,width,height);
		this.xoffset = xoffset;
		this.yoffset = yoffset;
	}

	 public boolean contains(double x, double y, double x1, double y1) {
		 for(int xx = (int)x;xx < x1;xx++){
			 for(int yy = (int)y;yy < y1;yy++){
				 if(contains(xx, yy))return true;
			 }
		 }

		 return false;

	}

	public boolean contains(double x, double y){
		if(x >= (position.x-xoffset) && x <= (position2.x-xoffset)){
			if(y >= (position.y-yoffset) && y <= (position2.y-yoffset)){
				return true;
			}
		}
		return false;
	}

	public boolean contains(Rectangle2 r){
		boolean result = contains((int)r.position.x-r.xoffset , (int)r.position.y-r.yoffset, (int)r.position2.x-r.xoffset, (int)r.position2.y-r.yoffset);
		return result;
	}

	public Rectangle2 add(Rectangle2 other){
        if(other.position.x < this.position.x ){
            this.position.x = other.position.x;
        }
        if(other.position.y < this.position.y){

            this.position.y = other.position.y;
        }
        if((other.position2.x) > (this.position2.x)){
            this.position2.x += (other.position2.x) - (this.position2.x);
        }

        if((other.position2.y) > (this.position2.y)){
            this.position2.y += (other.position2.y) - (this.position2.y);
        }


        return this;
    }
	
}
