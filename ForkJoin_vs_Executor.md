# ForkJoinPool vs ExecutorService

ForkJoinPoolExecutorServiceDivide-and-conquer problemsIndependent tasksRecursive algorithmsUnrelated jobsTasks create subtasksTasks generally don't create more tasksCPU-intensive workLong-running/background tasksUses work stealingUses a task queueOptimised for parallel recursionGeneral-purpose thread pool

---

# Use ForkJoinPool when...

The problem can be broken into **smaller pieces of the same problem**.

```
Problem
   |
---------
|       |
same   same
problem problem
```

Eventually every piece becomes small enough to solve directly.

Examples:

- Merge Sort
- Quick Sort
- Parallel Binary Tree Traversal
- Matrix Multiplication
- Image Processing
- File Indexing
- Recursive directory scanning
- Parallel DFS

### Example: Merge Sort

```
sort(1M elements)

       sort
      /    \\
 sort       sort
 /  \\       /  \\
...
```

Every node again performs sorting.

This recursive decomposition is exactly what `ForkJoinPool` was designed for.

---

# Use ExecutorService when...

Tasks are **independent**.

Example:

```
Send Email
Generate PDF
Update Database
Call Payment API
```

These have nothing to do with each other.

You simply submit them.

```
executor.submit(new EmailTask());
executor.submit(new PdfTask());
executor.submit(new PaymentTask());
```

No task creates more tasks.

---

# Real-world examples

## ExecutorService

Suppose an e-commerce website receives an order.

```
Order Received
```

Need to perform:

```
Store Order

Send Email

Update Inventory

Generate Invoice
```

These are independent.

```
ExecutorService executor = Executors.newFixedThreadPool(4);

executor.submit(new SaveOrderTask());
executor.submit(new EmailTask());
executor.submit(new InventoryTask());
executor.submit(new InvoiceTask());
```

Perfect use case.

---

## ForkJoinPool

Suppose you're sorting 50 million products.

```
Sort 50M Products
```

Split into

```
25M          25M
```

Each splits again.

```
12M 13M   12M 13M
```

Every subtask is still sorting.

This is recursive decomposition.

Perfect for `ForkJoinPool`.

---

## What I'll provide

- Code examples for both `ForkJoinPool` and `ExecutorService` implementations
- Specific problems or inputs you want parallelized
- Performance targets and test data
- Any constraints or platform details (CPU cores, memory)

---

# Why not ExecutorService for recursion?

You _can_ use it.

```
Future<Integer> left =
executor.submit(...);

Future<Integer> right =
executor.submit(...);

return left.get() + right.get() + 1;
```

But imagine a tree with one million nodes.

Each recursive call creates another task.

Eventually you'll have hundreds of thousands of queued tasks.

The executor has no knowledge of the recursive relationship between them.

---

ForkJoinPool instead uses **work stealing**.

Suppose:

```
Thread 1

Left subtree
```

```
Thread 2

Right subtree
```

If Thread 2 finishes,

```
Thread 2

steals

part of Thread 1's work
```

This automatic load balancing is what makes it efficient for recursive algorithms.

---

# CPU-bound vs I/O-bound

This is another good rule of thumb.

### CPU-bound

Examples:

- Sorting
- Searching
- Encryption
- Compression
- Mathematical computations

Use **ForkJoinPool** if the algorithm is recursive/divide-and-conquer.

---

### I/O-bound

Examples:

- Calling REST APIs
- Reading files
- Database queries
- Sending emails
- Kafka producers/consumers
- Network communication

Use **ExecutorService** because threads spend much of their time waiting for I/O rather than performing computation.

# Decision flow

```
            New Problem
               |
      --------------------------------
      |                              |
   Is it recursive?                 Independent tasks?
      |                              |
       Yes                            Yes
      |                              |
  Divide into subtasks?          Submit tasks
      |                              |
       Yes                      ExecutorService
      |
   ForkJoinPool
```

### A simple memory trick

- **ForkJoinPool = One big problem → split it into smaller versions of the same problem.**
- **ExecutorService = Many separate problems → run them concurrently.**
