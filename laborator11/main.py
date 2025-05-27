#lab11tema1

import asyncio
from asyncio import Queue

# Funcția care calculează suma 0 + 1 + ... + n
async def calculate_sum(queue: Queue, coroutine_id: int):
    while not queue.empty():
        n = await queue.get()
        total = n * (n + 1) // 2  # Formula directă
        print(f"Corutina {coroutine_id}: suma pentru n = {n} este {total}")
        await asyncio.sleep(0.1)  # simulăm o mică întârziere

async def main():
    # Coada cu 4 valori pentru n
    q = Queue()
    for value in [10, 100, 250, 1000]:
        await q.put(value)

    # Lansăm 4 corutine
    tasks = [asyncio.create_task(calculate_sum(q, i)) for i in range(4)]

    await asyncio.gather(*tasks)

# Rulăm programul
asyncio.run(main())