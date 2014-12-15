package package1;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс наиболее абстрактного физического объекта - как мат. тела, 
 * так и поля например.
 * @author ytsuken
 */
public abstract class PhysObject {
    /**
     * Дети =) Дочерние объекты
     */
    protected List<PhysObject> children = new LinkedList<PhysObject>();
    public PhysObject parent;
    
    /**
     * Добавить дочерний объект.
     * @param child Дочерний объект
     * @return True если объект согласился усыновить переданный объект, 
     * иначе - False
     */
    public boolean attach(PhysObject child) {
        if (!child.equals(this)) { 
            children.add(child);
            child.parent = this;
            return true;
        }
        return false;
    }
    /**
     * Отбавить дочерний объект.
     * @param child Дочерний объект
     * @return True если объект позволил отнять его ребенка
     */
    public boolean detach(PhysObject child) {
        children.remove(child);
        child.parent = null;
        return true;
    }
       
}
