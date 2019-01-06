/* package projetFac
import java.awt.{ Canvas, Dimension }
import java.awt.Color
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel

class Panneau extends JPanel {
 def testons (g: Graphics, x: Int, y: Int):Unit ={
   if(x > 0){
     g.drawRect(30*x, 30*y, 30, 30)
     testons (g, x-1, y)
   }else {
     if(y>0){
       testons (g, 5, y-1)
     }else{
       g.fillOval(35, 35, 20, 20)
       Unit
     }
   }
 }

 override def paintComponent(g: Graphics): Unit = { //x1, y1, width, height
   testons (g, 5, 5)
 }
}
object test2 extends App{
private[this] val dim = new Dimension(500, 400)
 val canvas = new Canvas() {
   setPreferredSize(dim)
   setMaximumSize(dim)
   setMinimumSize(dim)
   setFocusable(false)
 }
 val frame = new JFrame("name") {
   setSize(dim)
   setDefaultCloseOperation(3)
   setResizable(false)
   setLocationRelativeTo(null)
   setVisible(true)
   add(canvas)
   pack()


 }

 //Instanciation d'un objet JPanel
 val pan = new JPanel
 frame.setVisible(true)
 val p = new Panneau
 p.paintComponent(frame.getGraphics)
 pan.setBackground(Color.ORANGE)
 frame.setContentPane(pan)
 frame.setContentPane(p)
 frame.setVisible(true)
}*/