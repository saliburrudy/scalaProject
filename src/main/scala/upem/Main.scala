package projetFac

import upem.Manage._
import java.awt.{Canvas, Color, Dimension, Graphics, Image}
import javax.swing.JFrame
import javax.swing.JPanel
import java.io.File
import javax.imageio.ImageIO

case class Position(orientation: Char, point: Point)

trait Direction  {
  def l: Direction
  def r: Direction
}

object Direction {

}

class Panneau(px:Int, py:Int, maxX:Int, maxY: Int, orientation: Char, numTondeuse: Int) extends JPanel {
  def testArray(num:Int):Int = {
    num match {
      case 5 => 30*(0)+5
      case 4 => 30*(1)+5
      case 3 => 30*(2)+5
      case 2 => 30*(3)+5
      case 1 => 30*(4)+5
      case 0 => 30*(5)+5
      case _ => 0
    }
  }
  val mowerE = ImageIO.read(new File("/Users/salibur/Downloads/projetFac/src/main/scala/mowerE.png"))
  val mowerN = ImageIO.read(new File("/Users/salibur/Downloads/projetFac/src/main/scala/mowerN.png"))
  val mowerS = ImageIO.read(new File("/Users/salibur/Downloads/projetFac/src/main/scala/mowerS.png"))
  val mowerW = ImageIO.read(new File("/Users/salibur/Downloads/projetFac/src/main/scala/mowerW.png"))

  def testons (g: Graphics, x: Int, y: Int, numTondeuse: Int):Unit ={
    if(x >= 0){
      if(y >= 0) {
        g.drawRect(30 * x, 30 * y, 30, 30)
      }
      testons (g, x-1, y, numTondeuse)
    }else {
      if(y >= 0){
        testons (g, 5, y-1, numTondeuse)
      }else{
        orientation match {
          case 'N' => g.drawImage(mowerN, (30*px)+5, testArray(py), 20, 20, null)
          case 'E' => g.drawImage(mowerE, (30*px)+5, testArray(py), 20, 20, null)
          case 'S' => g.drawImage(mowerS, (30*px)+5, testArray(py), 20, 20, null)
          case 'W' => g.drawImage(mowerW, (30*px)+5, testArray(py), 20, 20, null)
        }
        g.drawString("Tondeuse numéro " + numTondeuse, (30 *maxX)+50 , 110)
        g.drawString(""+ px + " " +  py + " "  + orientation, (30 *maxX)+50 , 125)
        Unit
      }
    }
  }

  override def paintComponent(g: Graphics): Unit = { //x1, y1, width, height
    testons (g, maxX, maxY, numTondeuse)
  }
}

object newClass extends App{
  val maxX:Int = 5
  val maxY:Int = 5
  val indication:List[Char] = List('G', 'A', 'G', 'A', 'G', 'A', 'G', 'A', 'A')
  val indication2:List[Char] = List('A','A','D','A','A','D','A','D','D','A')
  val point:Point = Point(1, 2)
  val point2:Point = Point(3, 3)
  val positionT1:Position = Position('N', point)
  val positionT2:Position = Position('E', point2)

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

  def modifyOrientation(orientation:Char, indication:Char):Option[Char] = {
    orientation match {
      case 'N' => if (indication.equals('G')) Some('W') else Some('E')
      case 'E' => if (indication.equals('G')) Some('N') else Some('S')
      case 'S' => if (indication.equals('G')) Some('E') else Some('W')
      case 'W' => if (indication.equals('G')) Some('S') else Some('N')
      case _ => None
    }
  }

  def displayOptionPosition(some: Option[Position]) = {
    some match {
      case Some(x) => println(x.point.x + " " + x.point.y + " " + x.orientation)
      case None => println("Nothing in it")
    }
  }

  def displayPosition(position: Position, maxX: Int, maxY: Int, numTondeuse:Int) = {
    Thread.sleep(500)
    val pan = new JPanel
    frame.setVisible(true)
    val p = new Panneau(position.point.x, position.point.y, maxX, maxY, position.orientation, numTondeuse)
    frame.setContentPane(p)
    frame.setVisible(true)
    println(position.point.x + " " + position.point.y + " " + position.orientation, numTondeuse)
  }

  def modifyPoint(position: Position):Option[Point] = {
    position.orientation match {
      case 'N' => Some(Point(position.point.x, (if (position.point.y >= maxY) position.point.y else position.point.y+1 )))
      case 'E' => Some(Point((if (position.point.x >= maxX) position.point.x else position.point.x+1 ), position.point.y))
      case 'S' => Some(Point(position.point.x, (if (position.point.y <= 0) position.point.y else position.point.y-1 )))
      case 'W' => Some(Point((if (position.point.x <= 0) position.point.x else position.point.x-1 ), position.point.y))
    }
  }

  def testRecu(indication:List[Char], position: Position, maxX: Int, maxY: Int, numTondeuse:Int):Option[Position] = {
    displayPosition(position, maxX, maxY, numTondeuse)
    indication match {
      case l::tail  => l  match {
        case 'G' =>  modifyOrientation(position.orientation, 'G') match {
          case Some (p) => testRecu (tail, Position(p, position.point), maxX, maxY, numTondeuse)
          case None => None
        }
        case 'D' =>  modifyOrientation(position.orientation, 'D') match {
          case Some (p) => testRecu (tail, Position(p, position.point), maxX, maxY, numTondeuse)
          case None => None
        }
        case 'A' =>  modifyPoint(position) match {
          case Some (p) => testRecu(tail, Position(position.orientation, p), maxX, maxY, numTondeuse)
          case None => None
        }
        case _ => Some(position)
      }
      case Nil => Some(position)
    }

  }

  println("Tondeuse numéro 1")
  val position2 = testRecu(indication, positionT1, maxX, maxY, 1)
  println("Position finale tondeuse numéro 1 : ")
  displayOptionPosition(position2)

  println()
  Thread.sleep(2500)
  println("Tondeuse numéro 2")
  val position3 = testRecu(indication2, positionT2, maxX, maxY, 2)
  println("Position finale tondeuse numéro 2 : ")
  displayOptionPosition(position3)

}