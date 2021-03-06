package library

import org.lwjgl.glfw.GLFW.glfwGetTime
import org.lwjgl.glfw.GLFW.glfwSetTime

/** Facade for the GLFW Timer */
class Timer {
    private var startTime = 0D
    private var lastTime = 0D
    private var isRunning = false


    //Must call this method to use the timer
    def start(): Unit = {
        require(GLFW.isRunning)
        startTime = glfwGetTime()
        isRunning = true
    }

    def getTime(): Double = {
        if(isRunning)
            glfwGetTime() - startTime
        else
            -1D
    }

    def getLowPrecisionTime(): Double = ((getTime() * 1000).toInt).toDouble / 1000

    /** Returns the time elapsed since the last getDelta call for this timer object*/
    def getDelta(): Double = {
        var currentTime = getTime()
        val delta = currentTime - lastTime
        lastTime = currentTime
        delta
    }
}

object Timer {
    def getTime: Double = {
        require(GLFW.isRunning)
        glfwGetTime()
    }
    def getLowPrecisionTime: Double = ((Timer.getTime * 1000).toInt).toDouble / 1000

    def profile[T](label: String)(block: => T): T = {
        val t0 = Timer.getTime
        val result = block
        val t1 = Timer.getTime
        println(label + " elapsed time: " + (t1 - t0) + " seconds")
        result
    }

}
