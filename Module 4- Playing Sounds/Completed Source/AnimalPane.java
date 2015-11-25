import java.awt.Dimension;import java.io.IOException;import java.io.FileNotFoundException;import quicktime.app.QTFactory;import quicktime.app.anim.Compositor;import quicktime.app.image.GraphicsImporterDrawer; import quicktime.app.image.ImagePresenter; import quicktime.app.image.ImageUtil;import quicktime.app.players.MoviePresenter;import quicktime.app.players.QTPlayer;import quicktime.io.QTFile;import quicktime.io.OpenMovieFile;import quicktime.qd.QDRect;import quicktime.qd.QDGraphics;import quicktime.qd.QDColor; import quicktime.qd.QDConstants; import quicktime.qd.QDDrawer;import quicktime.std.image.Matrix;import quicktime.std.StdQTConstants;import quicktime.std.movies.Movie; import quicktime.std.image.GraphicsMode;import quicktime.QTSession;import quicktime.QTException; /** * QTZoo Module 4 - Playing a sound file * This application requires QuickTime for Java * * @author Levi Brown * @author Michael Hopkins * @author Apple Computer, Inc. * @version 3.1 11/16/1999 * * Copyright: 	� Copyright 1999 Apple Computer, Inc. All rights reserved. *	 * Disclaimer:	IMPORTANT:  This Apple software is supplied to you by Apple Computer, Inc. *				("Apple") in consideration of your agreement to the following terms, and your *				use, installation, modification or redistribution of this Apple software *				constitutes acceptance of these terms.  If you do not agree with these terms, *				please do not use, install, modify or redistribute this Apple software. * *				In consideration of your agreement to abide by the following terms, and subject *				to these terms, Apple grants you a personal, non-exclusive license, under Apple�s *				copyrights in this original Apple software (the "Apple Software"), to use, *				reproduce, modify and redistribute the Apple Software, with or without *				modifications, in source and/or binary forms; provided that if you redistribute *				the Apple Software in its entirety and without modifications, you must retain *				this notice and the following text and disclaimers in all such redistributions of *				the Apple Software.  Neither the name, trademarks, service marks or logos of *				Apple Computer, Inc. may be used to endorse or promote products derived from the *				Apple Software without specific prior written permission from Apple.  Except as *				expressly stated in this notice, no other rights or licenses, express or implied, *				are granted by Apple herein, including but not limited to any patent rights that *				may be infringed by your derivative works or by other works in which the Apple *				Software may be incorporated. * *				The Apple Software is provided by Apple on an "AS IS" basis.  APPLE MAKES NO *				WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED *				WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR *				PURPOSE, REGARDING THE APPLE SOFTWARE OR ITS USE AND OPERATION ALONE OR IN *				COMBINATION WITH YOUR PRODUCTS. * *				IN NO EVENT SHALL APPLE BE LIABLE FOR ANY SPECIAL, INDIRECT, INCIDENTAL OR *				CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE *				GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) *				ARISING IN ANY WAY OUT OF THE USE, REPRODUCTION, MODIFICATION AND/OR DISTRIBUTION *				OF THE APPLE SOFTWARE, HOWEVER CAUSED AND WHETHER UNDER THEORY OF CONTRACT, TORT *				(INCLUDING NEGLIGENCE), STRICT LIABILITY OR OTHERWISE, EVEN IF APPLE HAS BEEN *				ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. *  * Revision History * --------------------------------------------------------------------------------- * 12/02/99 MSH  cleaned up code, added comments *  */public class AnimalPane{	/**	 *  Public default constructor	 *  Creates the map button, and sets up the listeners	 */	public AnimalPane()	{		final QDRect size = new QDRect( Zoo4.WIDTH, Zoo4.HEIGHT );		try		{			loadSound( "data/zebra/Zebra.au" );			QDGraphics gw = new QDGraphics( size );			// create a new graphics object			compositor = new Compositor( gw, QDColor.white, 30, 1 );						QTFile imageFile = new QTFile( QTFactory.findAbsolutePath( "data/zebra/ZebraBackground.jpg" ));			GraphicsImporterDrawer drawer = new GraphicsImporterDrawer( imageFile );			ImagePresenter presenter = ImagePresenter.fromGraphicsImporterDrawer( drawer );			presenter.setLocation( 110, 110 );				// set location of movie within compositor				compositor.addMember( presenter, 3 );			// add image presenter to compositor in 2nd layer						addText( "data/zebra/Zebra.txt", 2, 15, 160, 415, 220 );  						Movie m = makeMovie( new QTFile( QTFactory.findAbsolutePath( "data/zebra/Zebra.mov" )));			md = new MoviePresenter( m );					// create presenter from movie file				compositor.addMember( md, 1 );					// add presenter to compositor			compositor.getTimer().setRate(1);				// start compositor			md.setRate(1);									// start movie			playSound();		}		catch( IOException e )								// catch any errors		{			e.printStackTrace();		}		catch( QTException e )		{			e.printStackTrace();		}	}	/**	 *  Plays the sound file associated with the current AnimalPane	 */	public void playSound()	{		if( player == null )			return;		try		{			player.setTime(0);			//Start the sound at the beginning            player.startTasking(); 		//Make sure the player gets time to play the sound            player.setRate(1); 			//Start playing		}		catch( QTException e )		{			e.printStackTrace();		}	}		/**	 *	Return the compositor associated with this pane	 */	public Compositor getCompositor( ) { return compositor; }		/**	 * Reads a text file from the disk and displays it in the compositor	 * @param textPath the relative path where the text is located	 * @param layer, the layer of the compositor to add the text to.	 * @param x the x coordinate location.	 * @param y the y coordinate location.	 * @param width the width of the area to display the text.	 * @param height the height of the area to display the text.	 */	protected void addText( String textPath, int layer, int x, int y, int width, int height )	{		try		{			TextPresenter text = new TextPresenter( textPath, new Dimension( width, height ));						ImagePresenter presenter = text.getPresenter();			Matrix theMatrix = new Matrix();			theMatrix.translate( (float) x, (float) y );			presenter.setMatrix( theMatrix );			compositor.addMember( presenter, layer );		}		catch ( QTException e )		{			e.printStackTrace();		}		catch ( FileNotFoundException e )		{			e.printStackTrace();		}		}	// MODULE 3 Addition	/**	 * Loads the specified sound file from disk and prepares it for playing	 */	protected void loadSound( String soundPath )	{		try		{			String soundLocation = QTFactory.findAbsolutePath( soundPath ).getPath();            //this call works with a file://, http://, rtsp://located movie			player = (QTPlayer)QTFactory.makeDrawable ("file://" + soundLocation);		}		catch( IOException e )		{			e.printStackTrace();		}		catch( QTException e )		{			e.printStackTrace();		}	}			/**	 * Opens the movie file and sets it up to be played.	 * @param f a QTFile representing the movie to initialize.	 * @return the movie, ready to play	 */	protected Movie makeMovie( QTFile f ) throws IOException, QTException	{		OpenMovieFile movieFile = OpenMovieFile.asRead(f);		Movie m = Movie.fromFile( movieFile );		m.getTimeBase().setFlags( StdQTConstants.loopTimeBase );			return m;		}		protected QTPlayer player;			protected Compositor compositor;	protected MoviePresenter md;}