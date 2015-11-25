import java.awt.*;import java.awt.event.*;import java.io.*;import java.util.Hashtable;import quicktime.*;import quicktime.qd.*;import quicktime.std.*;import quicktime.io.*;import quicktime.sound.*;import quicktime.std.image.*;import quicktime.std.music.*;import quicktime.std.movies.*;import quicktime.util.*;import quicktime.app.display.*;import quicktime.app.image.*;import quicktime.app.anim.*;import quicktime.app.actions.*;import quicktime.app.event.QTActionEvent;  import quicktime.app.event.QTActionListener;import quicktime.app.event.QTMouseTargetController; import quicktime.app.audio.*;import quicktime.app.QTFactory;import quicktime.std.movies.media.DataRef;import quicktime.app.players.QTPlayer;/** * QTZoo Module 7 - Switching between two compositors * The Map pane that contains the main map interface * * @author Levi Brown * @author Michael Hopkins * @author Apple Computer, Inc. * @version 1.0 10/21/1999 * Copyright: 	� Copyright 1999 Apple Computer, Inc. All rights reserved. *	 * Disclaimer:	IMPORTANT:  This Apple software is supplied to you by Apple Computer, Inc. *				("Apple") in consideration of your agreement to the following terms, and your *				use, installation, modification or redistribution of this Apple software *				constitutes acceptance of these terms.  If you do not agree with these terms, *				please do not use, install, modify or redistribute this Apple software. * *				In consideration of your agreement to abide by the following terms, and subject *				to these terms, Apple grants you a personal, non-exclusive license, under Apple�s *				copyrights in this original Apple software (the "Apple Software"), to use, *				reproduce, modify and redistribute the Apple Software, with or without *				modifications, in source and/or binary forms; provided that if you redistribute *				the Apple Software in its entirety and without modifications, you must retain *				this notice and the following text and disclaimers in all such redistributions of *				the Apple Software.  Neither the name, trademarks, service marks or logos of *				Apple Computer, Inc. may be used to endorse or promote products derived from the *				Apple Software without specific prior written permission from Apple.  Except as *				expressly stated in this notice, no other rights or licenses, express or implied, *				are granted by Apple herein, including but not limited to any patent rights that *				may be infringed by your derivative works or by other works in which the Apple *				Software may be incorporated. * *				The Apple Software is provided by Apple on an "AS IS" basis.  APPLE MAKES NO *				WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED *				WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR *				PURPOSE, REGARDING THE APPLE SOFTWARE OR ITS USE AND OPERATION ALONE OR IN *				COMBINATION WITH YOUR PRODUCTS. * *				IN NO EVENT SHALL APPLE BE LIABLE FOR ANY SPECIAL, INDIRECT, INCIDENTAL OR *				CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE *				GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) *				ARISING IN ANY WAY OUT OF THE USE, REPRODUCTION, MODIFICATION AND/OR DISTRIBUTION *				OF THE APPLE SOFTWARE, HOWEVER CAUSED AND WHETHER UNDER THEORY OF CONTRACT, TORT *				(INCLUDING NEGLIGENCE), STRICT LIABILITY OR OTHERWISE, EVEN IF APPLE HAS BEEN *				ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. *  *  */public class MapPane extends ZooPane{	public MapPane( )	{		try		{			QDRect size = new QDRect(MainFrame.WIDTH, MainFrame.HEIGHT);			QDGraphics gw = new QDGraphics (size);			compositor = new Compositor (gw, QDColor.white, 20, 1);						QTFile waterPict = new QTFile (QTFactory.findAbsolutePath ("data/map/water.pict"));			GraphicsImporterDrawer waterDrawer = new GraphicsImporterDrawer(waterPict);			waterDrawer.setDisplayBounds (size);			ImagePresenter water = ImagePresenter.fromGraphicsImporterDrawer(waterDrawer);			compositor.addMember(water, 6);			QTFile 				islandFile = new QTFile( QTFactory.findAbsolutePath( "data/map/island.pict" ));			ImagePresenter 		islandPresenter = ImagePresenter.fromFile( islandFile );			ImageDataSequence 	islandDS = new ImageDataSequence (islandPresenter.getDescription());						islandDS.addMember( islandPresenter.getImage() );			if ((QTSession.isCurrentOS(QTSession.kWin32) && QTSession.getQTMajorVersion() == 3) == false)	//doesn't work on QT3.0.2 on Win				islandDS = ImageUtil.makeTransparent ( islandDS, QDColor.white );			Matrix theMatrix = new Matrix();			theMatrix.translate( (float) 10, (float) 5 );			TwoDSprite island = new TwoDSprite( islandDS, theMatrix, true, 2 );						compositor.addMember(island, 4);						ctr = new QTMouseTargetController( false );			ctr.addQTMouseListener( new PaneMouseListener( QDColor.lightGray, QDColor.white, QDColor.darkGray ));						compositor.addController (ctr);						ctr.addMember( island );						TwoDSprite currentSprite;			areas = new Hashtable(6);		}		catch (Exception e)		{			e.printStackTrace();		}	}	/**	 * Called to do any setup after being set as the client of the QTCanvas.	 * May be used to start effects running, movies playing, etc.	 */	public void start()	{	}	/**	 * Called to do clean up after being removed as the client of the QTCanvas.	 * Should be used to stop effects running, movies playing, etc.	 */	public void stop()	{	}}