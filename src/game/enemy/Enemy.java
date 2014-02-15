package game.enemy;

import game.player.Player;
import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.sprite.Rectangle;
import game.state.StatePlaying;
import game.util.server.DataPacket;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Enemy {
    
    protected EntitySprite sprite;
    protected String spritepath;
    protected int spritePointer;
    protected int animationSpeed;
    
    protected int x;
    protected int y;
    protected double speed;
    protected int moveTimer;
    
    protected int stunTimer;
    
    protected int health;
    
    protected int lastAttackId=-1;

    protected Player player;
    
    protected boolean readyToDie = false;
    
    protected Color minimapColor;
    
    protected final int id;
    
    //Getters. These methods probably can be left alone.
    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return minimapColor; }
    
    public ImageMask getCollisionMask() {
        return sprite.getAnimationMask(spritePointer)
                .getImageMask(sprite.getAnim(spritePointer).getFrame());
    }
    
    // By default enemies don't have attacks.
    public Rectangle getAttackMask() { return null; }
    
    public Enemy(Player player) {
        this.player = player;
        this.id = this.hashCode();
        minimapColor = new Color(Color.red);//Red is default enemy color
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    //Game loop methods
    public void init(GameContainer container) throws SlickException {
        initializeVariables();
        initializeSprite();
        initializeAttack();
    }
    
    public void update(GameContainer container, int delta) {
        resolveInvulnerability(delta);
        move(delta);
        resolveCollision();
        resolveAttack(delta);
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.getAnim(spritePointer).draw(x,y,64,64);
        renderAttack();
        if (StatePlaying.DEBUG_MODE)
            renderDebugInfo(g);
    }
    
    // Miscelleneous universal methods.    
    protected void renderDebugInfo(Graphics g) {
        g.setColor(Color.white);
        g.drawString("x: " + String.valueOf(x),10+x+64,38+y+64);
        g.drawString("y: " + String.valueOf(y),10+x+64,52+y+64);
    }
    
    public boolean readyToDie() { return readyToDie; }
    
    protected void resolveInvulnerability(int delta) {
        if (stunTimer>0)
            stunTimer -= delta;
        else if (health<1)
            readyToDie = true;
    }
    
    protected int directionToPlayer() {
        int playerDistX = player.getX() - getX();
        int playerDistY = player.getY() - getY();
        return Math.abs(playerDistX) > Math.abs(playerDistY) ? 
                playerDistX > 0 ? 0 : 2 : playerDistY > 0 ? 3 : 1;
    }
    
    //Empty methods. These methods should be overriden
    protected void initializeVariables() { }
    protected void initializeSprite() throws SlickException { initializeMask(); }
    protected void initializeAttack() throws SlickException { }
    protected void move(int delta) { } // Default move behavior
    protected void resolveCollision() { }
    protected void resolveAttack(int delta) { }
    protected void renderAttack() { }
    public void resolveHit(int ox, int oy, int attackId) { }
    
    //Other methods. These can be overriden if necessary.
    protected void initializeMask() throws SlickException {
        sprite.setMasks(
                createMask(0),
                createMask(1),
                createMask(2),
                createMask(3)
        );
    }
    
    protected AnimationMask createMask(int index) {
        ImageMask[] masks = new ImageMask[4];
        for (int i=0;i<4;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i));
        }
        return new AnimationMask(masks);
    }

    public int getId() {
        return id;
    }
    
    public DataPacket getPacket() {
        DataPacket packet = new DataPacket();
        packet.add(x,0);
        packet.add(y,4);
        packet.add(id,8);
        return packet;
    }
}