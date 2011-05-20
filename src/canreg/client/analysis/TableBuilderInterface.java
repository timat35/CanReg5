/**
 * CanReg5 - a tool to input, store, check and analyse cancer registry data.
 * Copyright (C) 2008-2011  International Agency for Research on Cancer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Morten Johannes Ervik, CIN/IARC, ervikm@iarc.fr
 */
package canreg.client.analysis;

import canreg.common.Globals.StandardVariableNames;
import canreg.common.database.PopulationDataset;
import java.util.LinkedList;

public interface TableBuilderInterface {

    public enum FileTypes {

        ps,
        pdf,
        csv,
        html,
        txt,
        png,
        svg,
        wmf
    };

    public StandardVariableNames[] getVariablesNeeded();

    public FileTypes[] getFileTypesGenerated();

    public LinkedList<String> buildTable(
            String tableHeader, // Header of the table
            String reportFileName, // Base of the filename to be written
            int startYear, // Start year - for display
            int endYear, // End year - for display
            Object[][] incidenceData, // Incidence data with year as first column, followed by one column for each of the "variables needed" and then a column "cases" with the number of cases 
            PopulationDataset[] populations, // denominators
            PopulationDataset[] standardPopulations, // standard popluation
            LinkedList<ConfigFields> configList, // configuration fields
            String[] engineParameters, // parameters to the engine
            FileTypes fileType // the requested filetype
            )
            throws NotCompatibleDataException, TableErrorException;

    public boolean areThesePopulationDatasetsCompatible(PopulationDataset[] populations);
}