HDrawablePool pool;
HSwarm swarm;
HTimer timer;

void setup() {
	size(640,640);
	H.init(this).background(#202020).autoClear(true);
	smooth();

	final HColorPool colors = new HColorPool(#FFFFFF, #F7F7F7, #ECECEC, #333333, #0095a8, #00616f, #FF3300, #FF6600);

	swarm = new HSwarm()
		.goal(width/2,height/2)
		.speed(5)
		.turnEase(0.05f)
		.twitch(20)
	;

	pool = new HDrawablePool(40);
	pool.autoAddToStage()
		.add (
			new HRect()
			.rounding(4)
			.size(18,6)
		)
		.onCreate (
		    new HCallback() {
		    	public void run(Object obj) {
		    		HDrawable d = (HDrawable) obj;
					d
						.fill( colors.getColor() )
						.loc( 500, 100 )
						.anchorAt( H.CENTER )
					;

					// Add "d" to swarm's list of targets
					swarm.addTarget(d);
				}
			}
		)
	;

	timer = new HTimer()
		.numCycles( pool.numActive() )
		.interval(250)
		.callback(
			new HCallback() { 
				public void run(Object obj) {
					pool.request();
				}
			}
		)
	;
}

void draw() {
	H.drawStage();

	// draw an ellipse to show swarm point
	noFill(); strokeWeight(2); stroke(#0095a8);
	ellipse(width/2, height/2, 4, 4);

	// draw an ellipse to show creation point
	noFill(); strokeWeight(2); stroke(#FF3300);
	ellipse(500, 100, 4, 4);
}