package com.management.vehicle.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import com.management.vehicle.trip.Coordinate;
import org.checkerframework.checker.units.qual.C;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 * A simple sample application that shows
 * a OSM map of Europe containing a route with waypoints
 * @author Martin Steiger
 */
public class mainMap
{
    public static int[] convertToDMS(double coordinate) {
        // Lấy phần nguyên làm độ
        int degrees = (int) coordinate;
        // Lấy phần thập phân còn lại
        double fractionalPart = coordinate - degrees;

        // Tính phút từ phần thập phân
        double minutesDecimal = fractionalPart * 60;
        int minutes = (int) minutesDecimal;

        // Tính giây từ phần thập phân của phút
        double secondsDecimal = (minutesDecimal - minutes) * 60;
        int seconds = (int) Math.round(secondsDecimal);

        // Trả về mảng chứa độ, phút, giây
        return new int[]{degrees, minutes, seconds};
    }
    public static void display(Coordinate begin, Coordinate end)
    {
        JXMapViewer mapViewer = new JXMapViewer();

        // Display the viewer in a JFrame
        JFrame frame = new JFrame("JXMapviewer2 Example 2");
        frame.getContentPane().add(mapViewer);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);

        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        int[] lngDMS = convertToDMS(begin.getLng());
        int[] latDMS = convertToDMS(begin.getLat());

        GeoPosition begin_map = new GeoPosition(latDMS[0],latDMS[1],latDMS[2],lngDMS[0],lngDMS[1],lngDMS[2]);
        lngDMS = convertToDMS(end.getLng());
        latDMS = convertToDMS(end.getLat());

        GeoPosition end_map = new GeoPosition(latDMS[0],latDMS[1],latDMS[2],lngDMS[0],lngDMS[1],lngDMS[2]);
        // Create a track from the geo-positions
        List<GeoPosition> track = Arrays.asList(begin_map, end_map);
        RoutePainter routePainter = new RoutePainter(track);

        // Set the focus
        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);

        // Create waypoints from the geo-positions
        Set<Waypoint> waypoints = new HashSet<Waypoint>(Arrays.asList(
                new DefaultWaypoint(begin_map),
                new DefaultWaypoint(end_map)));

        // Create a waypoint painter that takes all the waypoints
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(waypoints);

        // Create a compound painter that uses both the route-painter and the waypoint-painter
        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        painters.add(routePainter);
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
    }

    public static void display(List<Coordinate> listCoordinate)
    {
        JXMapViewer mapViewer = new JXMapViewer();

        // Display the viewer in a JFrame
        JFrame frame = new JFrame("JXMapviewer2 Example 2");
        frame.getContentPane().add(mapViewer);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);

        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        List<GeoPosition> track = new ArrayList<>();

        for(Coordinate coordinate: listCoordinate) {
            int[] lngDMS = convertToDMS(coordinate.getLng());
            int[] latDMS = convertToDMS(coordinate.getLat());

            track.add(new GeoPosition(latDMS[0],latDMS[1],latDMS[2],lngDMS[0],lngDMS[1],lngDMS[2]));
        }

        RoutePainter routePainter = new RoutePainter(track);

        // Set the focus
        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);


        // Create waypoints from the geo-positions
        Set<Waypoint> waypoints = new HashSet<Waypoint>(Arrays.asList(
                new DefaultWaypoint(track.get(0)),
                new DefaultWaypoint(track.get(track.size() - 1))));

        // Create a waypoint painter that takes all the waypoints
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(waypoints);

        // Create a compound painter that uses both the route-painter and the waypoint-painter
        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        painters.add(routePainter);
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
    }
}
