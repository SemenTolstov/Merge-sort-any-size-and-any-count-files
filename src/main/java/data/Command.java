package data;

import data.DataType;
import data.SortType;

public class Command {
    public DataType dataType;
    public String[] inFiles;
    public SortType sortType;
    public String outFile;

    public Command(DataType dataType, String[] inFiles, SortType sortType, String outFile) {
        this.dataType = dataType;
        this.inFiles = inFiles;
        this.sortType = sortType;
        this.outFile = outFile;
    }
}
