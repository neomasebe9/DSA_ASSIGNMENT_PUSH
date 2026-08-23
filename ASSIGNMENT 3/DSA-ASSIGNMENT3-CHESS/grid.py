grid = [[1,2,3,4],[5,6,7,8],[9,10,11,12],[13,14,15,16]]

myString = ""

for row in grid:
    print("|", end=" ")
    myString = myString + "| "
    for elem in row:
        print(elem, end=" ")
        myString = myString + str(elem) + " "
    myString = myString + "|" + "\n"
    print("|")

print(myString)

for (int rowIndex = 0; rowIndex < numRows; rowIndex++){
    myString = myString + "| ";
    String[] row = grid[rowIndex]
    for (colIndex = 0; colIndex < numCols; colIndex++){
        String elem = row[colIndex];
        myString = myString + elem + " ";
    }
    myString = myString + "|" + "\n"
}