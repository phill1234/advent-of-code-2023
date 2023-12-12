using System;
using System.IO;
using System.Linq;
using System.Text.RegularExpressions;

class Program
{
    static void Main()
    {
        // Specify the path to your file
        string filePath = "input.txt";

        int sum = 0;

        // Define a regular expression pattern to match numbers
        Regex numberRegex = new Regex(@"\d+");

        // Read the file line by line
        using (StreamReader sr = new StreamReader(filePath))
        {
            string line;
            while ((line = sr.ReadLine()) != null)
            {
                int[] array1, array2;
                // Remove "Card X:" prefix
                string numbersPart = line.Substring(line.IndexOf(':') + 1).Trim();

                string[] numbers = numbersPart.Split('|');

                MatchCollection matches = numberRegex.Matches(numbers[0]);
                array1 = matches.Cast<Match>().Select(match => int.Parse(match.Value)).ToArray();

                matches = numberRegex.Matches(numbers[1]);
                array2 = matches.Cast<Match>().Select(match => int.Parse(match.Value)).ToArray();


                // Now you can use array1 and array2 as needed for each line
                // For example, you can print them
                Console.WriteLine("Array 1: " + string.Join(", ", array1));
                Console.WriteLine("Array 2: " + string.Join(", ", array2));

                // find equal numbers in both arrays
                var equalNumbers = array1.Intersect(array2);

                // points we have to add to sum
                int points = 0;
                for (int i = 0; i < equalNumbers.Count(); i++)
                {
                    if (i == 0)
                    {
                        points += 1;
                    }
                    else
                    {
                        points *= 2;
                    }
                }
                sum += points;
            }
        }
        Console.WriteLine("Total points: " + sum);
    }
}
