import re
from pprint import pprint

if __name__ == '__main__':
    seeds = []
    translation_maps = []
    actual_map = []
    with open('input.txt', 'r') as f:
        lines = f.readlines()
        for line in lines:
            if line.startswith('seeds'):
                # find all numbers in line with regex e.g. in seeds: 79 14 55 13
                seeds = re.findall(r'\d+', line)
                seeds = [int(i) for i in seeds]
                continue
            # if line starts with character, it is a translation map
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
    pprint(translation_maps)
    # initialize min_location with max int value in python
    locations = []
    for seed in seeds:
        # initialize location with seed value
        actual_location = int(seed)
        for translation_map in translation_maps:
            for translation_entry in translation_map:
                start = translation_entry[1]
                size = translation_entry[2]
                if actual_location in range(start, start + size):
                    actual_location = actual_location + (translation_entry[0] - translation_entry[1])
                    break

        locations.append(actual_location)
    pprint(locations)
    print(min(locations))
