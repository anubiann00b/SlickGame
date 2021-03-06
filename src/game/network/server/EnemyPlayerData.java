package game.network.server;

public class EnemyPlayerData {
    
    public int x;
    public int y;
    public int id;
    public int dir;
    public int frame;
    public int weapType;
    public int weapFrame;
    
    public EnemyPlayerData(int id) {
        this.id = id;
    } 
    
    public EnemyPlayerData(EnemyPlayerData e) {
        this(e.id,e.x,e.y,e.dir,e.frame,e.weapType,e.weapFrame);
    }
    
    public EnemyPlayerData(int id, int x, int y, int dir, int frame, int weapType,
            int weapFrame) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.dir = dir;
        this.frame = frame;
        this.weapType = weapType;
        this.weapFrame = weapFrame;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof EnemyPlayerData))
            return false;
        
        return ((EnemyPlayerData) o).id == id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        return hash;
    }
}