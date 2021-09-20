import data.Command;
import data.DataType;
import data.SortType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] files = {"src/main/resources/in1.txt", "src/main/resources/in2.txt", "src/main/resources/in3.txt", "src/main/resources/in4.txt"};
        try {
            Command command = readArgs(args);
            if (command.dataType.equals(DataType.Integer)) {
                if (command.sortType.equals(SortType.Asc)) {
                    superMerge(command.inFiles, command.outFile, DataType.Integer, SortType.Asc);
                } else {
                    superMerge(command.inFiles, command.outFile, DataType.Integer, SortType.Desc);
                }
            } else if (command.dataType.equals(DataType.String)) {
                if (command.sortType.equals(SortType.Asc)) {
                    superMerge(command.inFiles, command.outFile, DataType.String, SortType.Asc);
                } else {
                    superMerge(command.inFiles, command.outFile, DataType.String, SortType.Desc);
                }
            }

        } catch (NoArgsException e) {
            System.out.println(e.getMessage() + '\n' +
                    "Correct arguments: -s (for String) -i (for Integer) -d(for Descending)");
        }

    }

    static String mergeFilesAsc(String in1, String in2, String out, DataType dataType) throws IOException {

        BufferedReader br1 = Files.newBufferedReader(Paths.get(in1));
        BufferedReader br2 = Files.newBufferedReader(Paths.get(in2));
        BufferedWriter bw = Files.newBufferedWriter(Paths.get(out));
        if (dataType.equals(DataType.String)) {
            String str1 = br1.readLine();
            String str2 = br2.readLine();
            while (str1 != null && str2 != null) {
                if ((str1.compareTo(str2)) > 0) {
                    bw.write(str2 + "\n");
                    str2 = br2.readLine();
                } else {
                    bw.write(str1 + "\n");
                    str1 = br1.readLine();
                }
            }

            while (str1 != null) {
                bw.write(str1 + "\n");
                str1 = br1.readLine();
            }
            while (str2 != null) {
                bw.write(str2 + "\n");
                str2 = br2.readLine();
            }
        } else {
            try {
                String str1 = br1.readLine();
                String str2 = br2.readLine();
                while (str1 != null && str2 != null) {
                    int int1 = Integer.parseInt(str1);
                    int int2 = Integer.parseInt(str2);
                    if (int1 > int2) {
                        bw.write(int2 + "\n");
                        str2 = br2.readLine();
                    } else {
                        bw.write(int1 + "\n");
                        str1 = br1.readLine();
                    }
                }
                while (str1 != null) {
                    bw.write(str1 + "\n");
                    str1 = br1.readLine();
                }
                while (str2 != null) {
                    bw.write(str2 + "\n");
                    str2 = br2.readLine();
                }
            } catch (NumberFormatException ignored) {

            } finally {
                bw.close();
            }

        }
        bw.close();
        return out;
    }

    static String mergeFilesDesc(String in1, String in2, String out, DataType dataType) throws IOException {

        BufferedReader br1 = Files.newBufferedReader(Paths.get(in1));
        BufferedReader br2 = Files.newBufferedReader(Paths.get(in2));
        BufferedWriter bw = Files.newBufferedWriter(Paths.get(out));
        if (dataType.equals(DataType.String)) {
            String str1 = br1.readLine();
            String str2 = br2.readLine();
            while (str1 != null && str2 != null) {
                if ((str1.compareTo(str2)) < 0) {
                    bw.write(str2 + "\n");
                    str2 = br2.readLine();
                } else {
                    bw.write(str1 + "\n");
                    str1 = br1.readLine();
                }
            }

            while (str1 != null) {
                bw.write(str1 + "\n");
                str1 = br1.readLine();
            }
            while (str2 != null) {
                bw.write(str2 + "\n");
                str2 = br2.readLine();
            }
        } else {
            try {
                String str1 = br1.readLine();
                String str2 = br2.readLine();
                while (str1 != null && str2 != null) {
                    int int1 = Integer.parseInt(str1);
                    int int2 = Integer.parseInt(str2);
                    if (int1 > int2) {
                        bw.write(int2 + "\n");
                        str2 = br2.readLine();
                    } else {
                        bw.write(int1 + "\n");
                        str1 = br1.readLine();
                    }
                }
                while (str1 != null) {
                    bw.write(str1 + "\n");
                    str1 = br1.readLine();
                }
                while (str2 != null) {
                    bw.write(str2 + "\n");
                    str2 = br2.readLine();
                }
            } catch (NumberFormatException ignored) {

            } finally {
                bw.close();
            }

        }
        bw.close();
        return out;
    }

    static void superMerge(String[] files, String resultFile, DataType dataType, SortType sortType) throws IOException {
        Queue<String> queue = new LinkedList<>(Arrays.asList(files));
//        BufferedWriter bw = Files.newBufferedWriter(Paths.get(resultFile));
        new File("tmp").mkdirs();
        int increase = 1;
        String out;
        while (queue.size() != 1) {
            if(queue.size() == 2) {
                out = resultFile;
            } else {
                out = new File("tmp/" + increase + ".txt").getAbsolutePath();
            }
//            File file = new File("tmp/" + increase + ".txt");
            if (sortType.equals(SortType.Asc)) {
                queue.offer(mergeFilesAsc(queue.poll(), queue.poll(), out, dataType));
            } else {
                queue.offer(mergeFilesDesc(queue.poll(), queue.poll(), out, dataType));
            }
            increase++;
        }
        Files.walk(Path.of("tmp"))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
//        bw.write(queue.peek());
//        bw.close();
    }


    static Command readArgs(String[] args) {
        if (args.length == 0) throw new NoArgsException("Incorrect arguments");
        DataType dataType = switch (args[0]) {
            case "-i" -> DataType.Integer;
            case "-s" -> DataType.String;
            default -> throw new NoArgsException("Incorrect arguments");
        };
        SortType sortType = args[1].equals("-d") ? SortType.Desc : SortType.Asc;
        int inStarIndex = args[1].equals("-d") ? 2 : 1;

        String outFile = args[args.length - 1];
        String[] inFiles = Arrays.copyOfRange(args, inStarIndex, args.length - 1);
        return new Command(dataType, inFiles, sortType, outFile);
    }


}