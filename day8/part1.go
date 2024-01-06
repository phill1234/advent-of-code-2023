package main

import (
	"bufio"
	"fmt"
	"os"
	"regexp"
)

type path struct {
	left  string
	right string
}

func extractCapitalizedElements(line string) map[string]map[string]string {
	result := make(map[string]map[string]string)

	// Regular expression to match capitalized words and the associated values
	re := regexp.MustCompile(`([A-Z]+)\s*=\s*\(([A-Z]+),\s*([A-Z]+)\)`)

	// Find all matches in the input line
	matches := re.FindAllStringSubmatch(line, -1)

	// Process each match and populate the result map
	for _, match := range matches {
		key := match[1]
		leftValue := match[2]
		rightValue := match[3]

		// Create a map for the current key and add the values
		result[key] = map[string]string{
			"L": leftValue,
			"R": rightValue,
		}
	}

	return result
}

func part1() {
	directions := ""
	// dictionary with right and left
	pathsMap := make(map[string]path)

	readFile, err := os.Open("input.txt")

	if err != nil {
		fmt.Println(err)
	}
	fileScanner := bufio.NewScanner(readFile)

	fileScanner.Split(bufio.ScanLines)

	var lineIndex int = 0
	for fileScanner.Scan() {
		// PART 1

		// first line
		if lineIndex == 0 {
			directions = fileScanner.Text()
		}

		// second line
		if lineIndex == 1 {
			lineIndex++
			continue
		}

		// build map
		if lineIndex > 1 {
			// extract right and left
			paths := extractCapitalizedElements(fileScanner.Text())
			for key, value := range paths {
				pathsMap[key] = path{value["L"], value["R"]}
			}
		}
		lineIndex++
	}

	// now we have to travel the directions in the map
	// we start from the first entry and we go left or right depending on the directions
	// our node to find
	// our target node is ZZZ
	foundZZZ := false
	currentNode := "AAA"
	directionsTemp := directions
	steps := 0
	for !foundZZZ {
		// increase steps
		steps++
		// if directionsTemp is empty we have to start from the beginning in the directions
		if len(directionsTemp) == 0 {
			directionsTemp = directions
		}
		// get the next direction
		nextDirection := string(directionsTemp[0])
		// remove the first character from the directions
		directionsTemp = directionsTemp[1:]
		// get the next node
		nextNode := ""
		if nextDirection == "L" {
			nextNode = pathsMap[currentNode].left
		} else {
			nextNode = pathsMap[currentNode].right
		}
		// if the next node is ZZZ we have found it
		if nextNode == "ZZZ" {
			foundZZZ = true
		} else {
			// otherwise we have to continue
			currentNode = nextNode
		}
	}
	fmt.Println("Part 1 Result:")
	fmt.Println(steps)

	readFile.Close()
}
