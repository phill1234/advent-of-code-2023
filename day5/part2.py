import re
from pprint import pprint

if __name__ == '__main__':
    seeds = []
    seed_ranges = []
    translation_maps = []
    actual_map = []
    with open('input.txt', 'r') as f:
        lines = f.readlines()
        for line in lines:
            if line.startswith('seeds'):
                seeds = re.findall(r'\d+', line)
                seeds = [int(i) for i in seeds]
                # iterate over seeds in two tuples step by step
                for seed1, seed2 in zip(seeds[::2], seeds[1::2]):
                    overlap_start = seed1
                    overlap_end = seed1 + seed2
                    seed_ranges.append((overlap_start, overlap_end))
                continue
            # if line starts with a character, it is a translation map
            if line[0].isalpha():
                if len(actual_map) > 0:
                    translation_maps.append(actual_map)
                actual_map = []
                continue

            if line[0].isdigit():
                translation_entry = re.findall(r'\d+', line)
                translation_entry = [int(i) for i in translation_entry]
                actual_map.append(translation_entry)
                continue
        # append last map
        translation_maps.append(actual_map)

    print(seed_ranges)

    # initialize min_location with max int value in python
    locations = []

    for translation_map in translation_maps:
        # Reset overlap_locations for each translation_map
        overlap_locations = []
        while len(seed_ranges) > 0:
            start_range, end_range = seed_ranges.pop()
            for translation_entry in translation_map:
                overlap_start = max(start_range, translation_entry[1])

                # 14 - 18 und 20 - 22
                overlap_end = min(end_range, translation_entry[1] + translation_entry[2])
                if overlap_start < overlap_end:
                    overlap_locations.append((overlap_start - translation_entry[1] + translation_entry[0],
                                              overlap_end - translation_entry[1] + translation_entry[0]))
                    if overlap_start > start_range:
                        seed_ranges.append((start_range, overlap_start))
                    if end_range > overlap_end:
                        seed_ranges.append((overlap_end, end_range))
                    break
            else:
                overlap_locations.append((start_range, end_range))
        seed_ranges = overlap_locations

    print(min(seed_ranges)[0])
