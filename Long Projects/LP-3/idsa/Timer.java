package idsa;

public class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.nanoTime();
            ready = false;
        }

        public void start() {
            startTime = System.nanoTime();
            ready = false;
        }

        public Timer end() {
            endTime = System.nanoTime();
            elapsedTime = endTime-startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() { if(!ready) { end(); }  return elapsedTime; }

        public long memory()   { if(!ready) { end(); }  return memUsed; }

	public void scale(int num) { elapsedTime /= num; }
	
        public String toString() {
            if(!ready) { end(); }
            return "Time: " + elapsedTime/1000 + " usec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
        }
    }