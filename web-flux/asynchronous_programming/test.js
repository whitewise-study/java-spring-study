const solution = (weights) => {

    let count = 0;
    let duple = new Set();

    for (let i = 0; i < weights.length; i++) {
        let map = new Map();
        let weight = weights[i];
        map.set(weight, 1);
        map.set(weight * 2, 2);
        map.set(weight * 3, 3);
        map.set(weight * 4, 4);

        for (let j = 0; j < weights.length; j++) {
            const weight2 = weights[j];

            if (j === i) {
                continue;
            }

            let breakFlag = false;

            if (map.has(weight2)) {
                let max = Math.max(map.get(weight2) , 1);
                let min = Math.min(map.get(weight2) , 1);

                if ( duple.has(weight + "" + weight2 + "" + max + "" + min) || (max === 1 && min !== 1) || (min === 1 && max !== 1)){
                    continue
                }

                count++;
                duple.add(weight + "" + weight2 + "" + max + "" + min);
                break;
            } else if (map.has(weight2 * 2)) {
                let max = Math.max(map.get(weight2 * 2) , 2);
                let min = Math.min(map.get(weight2 * 2) , 2);

                if ( duple.has(weight + "" + weight2 + "" + max + "" + min)|| (max === 1 && min !== 1) || (min === 1 && max !== 1)){
                    continue
                }

                if (map.get(weight2 * 2) === 1) {
                    break;
                }
                count++;
                duple.add(weight + "" + weight2 + "" + max + "" + min);
                break;
            } else if (map.has(weight2 * 3)) {
                let max = Math.max(map.get(weight2 * 3) , 3);
                let min = Math.min(map.get(weight2 * 3) , 3);
                if ( duple.has(weight + "" + weight2 + "" + max + "" + min)|| (max === 1 && min !== 1) || (min === 1 && max !== 1)){
                    continue
                }
                if (map.get(weight2 * 3) === 1) {
                    break;
                }
                count++;
                duple.add(weight + "" + weight2 + "" + max + "" + min);
                break;
            } else if (map.has(weight2 * 4)) {
                let max = Math.max(map.get(weight2 * 4) , 4);
                let min = Math.min(map.get(weight2 * 4) , 4);
                if ( duple.has(weight + "" + weight2 + "" + max + "" + min)|| (max === 1 && min !== 1) || (min === 1 && max !== 1)){
                    continue
                }
                if (map.get(weight2 * 2) === 1) {
                    break;
                }
                count++;
                duple.add(weight + "" + weight2 + "" + max + "" + min);
                break;
            }
        }
    }

    duple.values();
    for (const dupleElement of duple) {
        console.log(dupleElement)
    }

    return count;
}

console.log(solution([100, 180, 360, 100, 270]));
