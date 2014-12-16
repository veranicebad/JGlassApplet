/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import javax.media.j3d.BoundingBox;
import javax.vecmath.Point3d;

/**
 *
 * @author Vera
 */
public class MyBounds extends BoundingBox{
    
    
    public MyBounds(){
        
        this.getLower(null);
    }
    
    public Point3d getUpper(){
        Point3d p = new Point3d();
        super.getUpper(p);
        return p;
    }
    
    public Point3d getLower(){
        Point3d p = new Point3d();
        super.getLower(p);
        return p;
    }
}
