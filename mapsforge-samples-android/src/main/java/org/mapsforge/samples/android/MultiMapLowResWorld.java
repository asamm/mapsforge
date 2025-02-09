/*
 * Copyright 2014 Ludwig M Brinckmann
 * Copyright 2015-2019 devemux86
 * Copyright 2024 Sublimis
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.mapsforge.samples.android;

import android.content.Context;
import android.os.Bundle;

import org.mapsforge.map.datastore.MapDataStore;
import org.mapsforge.map.datastore.MultiMapDataStore;
import org.mapsforge.map.reader.MapFile;

import java.io.File;

/**
 * Illustrates the use of the MultiMapDataStore concept.
 * This example uses the low resolution world.map file to display land/sea areas for zoom levels
 * 0-7, and higher res maps for higher zoom levels.
 * This example requires a world.map file in the external files dir, the start of the Samples application
 * should download this automatically.
 */
public class MultiMapLowResWorld extends DefaultTheme {
    private MultiMapDataStore multiMapDataStore;
    private MapFile worldMapFile;

    @Override
    public MapDataStore getMapFile() {
        return this.multiMapDataStore;
    }

    /**
     * @return the first map file.
     */
    protected MapDataStore getMapFile1() {
        return super.getMapFile();
    }

    /**
     * @return the second map file.
     */
    protected MapDataStore getMapFile2() {
        return this.worldMapFile;
    }

    /**
     * @return the low res world map file.
     */
    public static File getWorldMapFile(Context context) {
        return new File(context.getExternalFilesDirs(null)[0], getWorldMapFileName());
    }

    /**
     * @return the name of the low res world map file.
     */
    public static String getWorldMapFileName() {
        return "world.map";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        worldMapFile = new MapFile(getWorldMapFile(this)) {
//            @Override
//            public boolean supportsTile(Tile tile) {
//                // Example low res world map has sufficient detail up to zoom level 7
//                return tile.zoomLevel <= 10 && super.supportsTile(tile);
//            }
        };
        worldMapFile.setPriority(-1);
        multiMapDataStore = new MultiMapDataStore(MultiMapDataStore.DataPolicy.DEDUPLICATE);
        MapFile mapFile1 = (MapFile) getMapFile1();
        //mapFile1.restrictToZoomRange((byte) 8, Byte.MAX_VALUE);
        multiMapDataStore.addMapDataStore(mapFile1, true, true);
        multiMapDataStore.addMapDataStore(getMapFile2(), false, false);

        super.onCreate(savedInstanceState);
    }
}
