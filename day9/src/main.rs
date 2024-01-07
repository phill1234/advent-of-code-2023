use std::result;

fn main() {
    let mut results: Vec<i32> = Vec::new();
    // read the input.txt file line by line
    let input = std::fs::read_to_string("input.txt").unwrap();
    // split the input into a vector of strings
    let input: Vec<&str> = input.split("\n").collect();
    // print the length of the vector
    let sequences: Vec<Vec<i32>> = input
        .iter()
        .map(|s| {
            s.split_whitespace()
                .map(|num| num.parse().unwrap())
                .collect()
        })
        .collect();

    for sequence in sequences {
        let result_entry: i32 = analyze_sequence(sequence);
        results.push(result_entry);
    }

    // get sum of the results
    let sum: i32 = results.iter().sum();
    println!("Sum: {}", sum);
}
fn analyze_sequence(sequence: Vec<i32>) -> i32 {
    let mut temp_sequences: Vec<Vec<i32>> = Vec::new();
    temp_sequences.push(sequence.clone());

    // FIRST STEP CREATE TEMPSEQUENCES
    loop {
        let actual_sequence = temp_sequences.last().unwrap().to_vec();
        let mut temp_sequence: Vec<i32> = Vec::new();
        for (index, entry) in actual_sequence.iter().enumerate() {
            // last element skip
            if index == actual_sequence.len() - 1 {
                break;
            }
            let next_entry = actual_sequence[index + 1];
            let entry_for_new_sequence = next_entry - entry;
            temp_sequence.push(entry_for_new_sequence);
        }
        temp_sequences.push(temp_sequence.clone());
        // finish the loop if the temp_sequence contains only 0 elements
        if temp_sequence.iter().all(|&x| x == 0) {
            break;
        }
    }
    println!("Tempsequences after first step: {:?}", temp_sequences);
    // reverse the temp_sequences
    temp_sequences.reverse();
    // SECOND STEP SUM UP THE LAST ELEMENTS
    // iterate over the temp_sequences size with index manually
    for index in 0..temp_sequences.len() {
        // last element skip
        if index == temp_sequences.len() - 1 {
            break;
        }
        // get the last element of the temp_sequence
        let last_element = temp_sequences[index].last().unwrap();
        // get the last element of the next temp_sequence
        let next_last_element = temp_sequences[index + 1].last().unwrap();
        // clone before modifying
        let mut next_sequence = temp_sequences[index + 1].clone();
        // push the sum of the last elements to the next temp_sequence
        let new_element = last_element + next_last_element;
        next_sequence.push(new_element);
        // update the temp_sequences with the modified sequence
        temp_sequences[index + 1] = next_sequence;
    }
    // print first element of the temp_sequences
    println!("Tempsequences after second step: {:?}", temp_sequences);
    // return the first element of the temp_sequences
    // last entry of temp_sequences is the result because of the reverse
    let result_sequence = temp_sequences.last().unwrap().to_vec();

    return result_sequence.last().unwrap().to_owned();
}
