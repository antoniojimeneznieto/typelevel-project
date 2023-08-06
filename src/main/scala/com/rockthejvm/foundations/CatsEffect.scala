package cheatsheet



object CatsEffect {
  /*                            BLOCK 1 & 2: IO & CONCURRENCY                              */
  trait IO[A] {

    /**                                   BLOCK 1: IO
     *
     *   - IO are stack recursively safe (implicit tailrec)
     *   - Map/flatMap make the computation seq (i.e we complete first one before mapping to the next IO)
     */
    def pure(value: A): IO[A] // Wrap in IO a pure functional computation or value

    def delay(thunk: => A): IO[A] // Wrap in IO a non pure computation

    def defer(thunk: => IO[A]): IO[A] // delay(...).flatten == Great for nested IO

    def map[B](f: A => B): IO[B]

    def flatMap[B](f: A => IO[B]): IO[B]

    def *>[B](that: IO[B]): IO[B] // Map but we ignore the result of the first IO

    def <*[B](that: IO[B]): IO[A] // Map but we ignore the result of the second IO

    def >>[B](that: => IO[B]): IO[B] // Map but we ignore the result of the first IO. Evaluate lazily, suitable for recursion

    def foreverM: IO[Nothing] // Runs forever the IO, over and over

    def as[B](b: B): IO[B] // Replace the value of the IO with the given value

    def void: IO[Unit] // Replace the value of the IO with ()

    def raiseError(t: Throwable): IO[A] // == Delay but with a more expressive name

    def attempt: IO[Either[Throwable, A]] // Map the IO result with case Left(error) and case Right(value: A)

    def redeem[B](recover: Throwable => B, map: Int => B): IO[B] // (ex => { doSomethingWith(ex); B }, value => f(value))

    def redeemWith[B](recover: Throwable => IO[B], bind: A => IO[B]): IO[B] // (ex => { IO(doSomethingWith(ex) ; B }, value => IO(f(value)))

    import scala.concurrent.duration.FiniteDuration
    def sleep(delay: FiniteDuration): IO[Unit] // Must be concatenated with Map/flatMap otherwise it won't sleep the computation. (Use Thread.sleep instead)

    def println(a: A): IO[Unit] // == IO.delay(println)

    import cats.syntax.apply._
    def mapN[Z](ios: IO[Any]*)(f: (Any, Any) => Z): IO[Z] // Map a N tuple of IO's

    import cats.effect.implicits._
    import cats.syntax.parallel._
    def parMapN[Z](ios: IO[Any]*)(f: (Any, Any) => Z): IO[Z] // parMap a N tuple of IO's

    import cats.Traverse
    import cats.instances.list._
    import cats.syntax.traverse._
    def traverse[B](fa: List[A])(f: A => IO[B]): IO[List[B]] // Traverse a List an Map each element and generate IO[List[B]]

    import cats.syntax.parallel._
    def parTraverse[B](fa: List[A])(f: A => IO[B]): IO[List[B]] // parTraverse

    def sequence(fga: List[IO[A]]): IO[List[A]] // Convert List[IO[A]] in IO[List[A]]

    def parSequence(Fga: List[IO[A]]): IO[List[A]] // parSequence

    def tupled[B](ioa: IO[A], iob: IO[B]): IO[(A, B)] // Computes the first IO and then the second one and return an IO of the result

    def parTupled[B](ioa: IO[A], iob: IO[B]): IO[(A, B)] // Compute in parallel both IO and return a IO of the result

    /**                                   BLOCK 2: CONCURRENCY
     *
     *   - onCancel: fin is an IO[Unit] (we can make it doing .void) that will run in case of cancellation
     *   - race: The result is an Either, we can map it with a partial function to case Left(value) case Right(value)
     */

    def start: IO[Fiber[IO, Throwable, Int]] // We start the computation of the IO on another thread

    def onCancel(fin: IO[Unit]): IO[A] // If the IO gets cancel during its execution it will run fin

    def timeout(duration: FiniteDuration): IO[A] // Returns the IO that completes with the expected result within the duration or throw TimeoutException

    import cats.effect.kernel.Poll
    def uncancelable(body: Poll[IO] => IO[A]): IO[A] // IO.uncancelable {poll => computation()} If we wrap the computation on poll(...) it will make it cancelable

    def canceled: IO[Unit] // We manually cancel the computation of the IO

    def blocking(thunk: => A): IO[A] // Evaluate the IO on a thread from ANOTHER thread pool specific for blocking calls

    def cede: IO[Unit] // Shift the execution to another thread
    
    import scala.concurrent.ExecutionContext
    def evalOn(ec: ExecutionContext): IO[A] // Shift the execution of the current IO to the ec

    def race[B](left: IO[A], right: IO[B]): IO[Either[A, B]] // Both IOs run on separete fibers, the first to finish complete the result, the loser get canceled

    import cats.effect.kernel.Outcome
    def racePair[B](left: IO[A], right: IO[B]): IO[Either[(Outcome[IO, Throwable, A], Fiber[IO, Throwable, B]), // (winner result, loser fiber), (loser fiber, winner result)
                                                  (Fiber[IO, Throwable, A], Outcome[IO, Throwable, B])]] // Both IOs run on separete fibers but we get control of the loser fiber 

    def async_(cb: (Either[Throwable, A] => Unit) => Unit): IO[A] // CE thread blocks until the cb is invoked in some other thread

    import scala.concurrent.Future
    def fromFuture(fut: IO[Future[A]]): IO[A] // async_ using the Future to do the computation on its threadpool, finally return the value to the IO cb

    def never: IO[A] // If we do IO.async_[A](_ => ()) it will block forever because the cb will never be invoked

    /**                                 BLOCK 3: RESOURCES
     *
     *   - gurantee and guaranteeCase are the prelude to Bracket and Resources. Prioritize the use of Resources and not guarantees
     *   - Bracket is equivalent to try{} catch{} finally{} from mainstream languages (Pure FP)
     */

    def guarantee(finalizer: IO[Unit]): IO[A] // Execute finalizer when finished, either in success, error or canceled

    def guaranteeCase(finalizer: Outcome[IO, Throwable, A] => IO[Unit]): IO[A] // Partial function with 3 possible results (success, error, canceled) and execute finalizer in each case

    def bracket[B](use: A => IO[B])(release: A => IO[Unit]): IO[B] // Once we acquire a resource inside the IO, we can try to use it and whatever happens we will execute the release function


    /**                                 BLOCK 4: COORDINATION
     *
     *   - If we create a Ref/Deferred it will be contained in an IO. Therefore, to modify it we need to do flatMap on the IO before using its methods.
     *   - If we follow the usual workflow, we create the IO[Ref/Deferred[IO, A]] and then inside the for we map it to the next step, passing the Ref/Deferred and not the IO[Ref/Deferred]
     */

     def ref[B](b: B): IO[Ref[IO, B]] // Another way to create an IO[Ref] which is a PF atomic value

     def deferred[B]: IO[Deferred[IO, B]]
    
  }

  trait Fiber[F[_], E, A] {
    /**                                 BLOCK 2: CONCURRENCY
     *
     *   - We can import cats.effect.{Fiber, Outcome} and cats.effect.Outcome.{Canceled, Errored, Succeded}
     *   - Map/flatMap make the computation seq (i.e we complete first one before mapping to the next IO)
     */

    def join: F[Outcome[F, E, A]] // We wait on the current fiber to the completition of this other fiber and returns Outcome[F,E,A]

    def cancel: F[Unit] // We cancel the current fiber

    trait Outcome[F[_], E, A] // Result of the execution of a Fiber. We can match Outcome with its subclasses
    final case class Succeeded[F[_], E, A](fa: F[A]) extends Outcome[F, E, A]
    final case class Errored[F[_], E, A](e: E) extends Outcome[F, E, A]
    final case class Canceled[F[_], E, A]() extends Outcome[F, E, A]

  }

  /*                                 BLOCK 3: RESOURCES                                    */

  trait Resource[F[_], A] {
    /*
     *   - Once we use the Resource, we obtain an IO that we can run on a fiber.
     *   - If we need nested resources (map/flatMap) use the for comprehension for clarity
     */

    def make(acquire: F[A])(release: A => F[Unit]): Resource[F, A] // Creates a resource

    def pure[A](a: A): Resource[F, A] // Lifts a pure value into a resource. The resource has a no-op release.

    def use[B](f: A => IO[B]): IO[B] // Allocate the resource and apply the given function. The resource is released as soon as F[B] is completed

    def map[B](f: A => B): Resource[F, B]

    def flatMap[B](f: A => Resource[F, B]): Resource[F, B]

    def evalMap[B](f: A => IO[B]): Resource[F, B] // applies an effectful transformation to the allocated resource

    def evalTap[B](f: A => IO[B]): Resource[F, A] // applies an effectful transformation to the allocated resource while maintaining the original resource

    import cats.arrow.FunctionK
    def mapK[G[_]](f: FunctionK[IO, G])(implicit F: MonadCancel[IO, ?], G: MonadCancel[G, ?]): Resource[G, A] // mapK(LiftIO.liftK) lift from IO to F

    def onCancel(fin: Resource[IO, Unit]): Resource[IO, A] // Run fin in case of cancellation

    def onFinalize(finalizer: IO[Unit]): Resource[IO, A] // Runs finalizer at the end in any case (success, cancellation or error)

    def handleErrorWith[B, E](f: E => Resource[IO, B]): Resource[IO, B]
    
    def surround[B](gb: IO[B]): IO[B] // Acquires the resource, runs gb and closes the resource once gb terminates, fails or gets interrupted

    def race[B](that: Resource[IO, B]): Resource[IO, Either[A, B]] // Races the evaluation of two resource allocations and returns the result of the winner
  }

  /*                                 BLOCK 4: COORDINATION                                 */

  trait Ref[F[_], A] {

      def of(a: A): IO[Ref[IO, A]] // Creates an IO[Ref]: Ref[IO].of()

      def set(a: A): IO[Unit] // Set the value of the Ref 

      def get: IO[A] // We get the value of the Ref

      def getAndSet(a: A): IO[A] // We get the value first and then we Set to a new value

      def update(f: A => A): IO[Unit] // We update the value taking the current value and processing it

      def updateAndGet(f: A => A): IO[A] // We update it and then we get the new value

      def modify[B](f: A => (A, B)): IO[B] // We modify the value to a new type

      /*                                 The usual Workflow:                                 */
      import cats.syntax.parallel._
      def task(workload: String, total: cats.effect.kernel.Ref[cats.effect.IO, Int]): cats.effect.IO[Unit] // Count number of words and sum it to total
      
      for {
        initialCount <- cats.effect.kernel.Ref[cats.effect.IO].of(0) // We create a global Ref to be shared between the threads
        _ <- List("This is an", "example of code").parTraverse(task(_, initialCount)) // We apply the function to each String and we count on the shared Ref
      } yield ()
  }

  trait Deferred[F[_], A] {

    def apply(): IO[Deferred[IO, A]] // Creates a new Deferred

    def get: IO[A] // Obtains the value of the Deferred or waits until it has been inserted/completed

    def complete(a: A): IO[Boolean] // Insert a value on the Deferred and notifies any readers blocked on a get

      /*                                 The usual Workflow:                                 */
    def consumer(signal: cats.effect.kernel.Deferred[cats.effect.IO, Int]): cats.effect.IO[Unit] // print something and waits to get the value in the Deferred
    def producer(signal: cats.effect.kernel.Deferred[cats.effect.IO, Int]): cats.effect.IO[Unit] // do some heavy computations and insert the result in the Deferred

    for {
      signal <- cats.effect.kernel.Deferred[cats.effect.IO, Int]
      fibConsumer <- consumer(signal).start
      fibProducer <- producer(signal).start
      _ <- fibProducer.join 
      _ <- fibConsumer.join
    } yield ()
  }

  trait Mutex {
      def acquire: IO[Unit]

      def release: IO[Unit]     

  }

  trait Semaphore[F[_]] {

    def apply(n: Long): IO[Semaphore[F]] // Creates a semaphore with n locks
      
    def acquire: IO[Unit] // Acquire one lock if there are no locks free it will block until one lock is released
      
    def release: IO[Unit] // Release one lock

    def acquireN(n: Long): IO[Unit] // Acquire N locks, if there are no locks free it blocks until N locks are released
    
    def releaseN(n: Long): IO[Unit] // Release N locks

      /*                                 The usual Workflow:                                 */
    def doWorkWhileLoggedIn(): IO[Int] // Wait 1 second and return a random number
    def login(id: Int, sem: cats.effect.std.Semaphore[cats.effect.IO]): cats.effect.IO[Int] // acquire a lock, doWorkWhileLoggedIn(), release the lock return value from doWorkWhileLoggedIn
    
    // THIS
    for {
      sem <- cats.effect.std.Semaphore[cats.effect.IO].apply(2)
      user1Fib <- login(1, sem).start
      user2Fib <- login(2, sem).start
      user3Fib <- login(3, sem).start 
      _ <- user1Fib.join
      _ <- user2Fib.join
      _ <- user3Fib.join
    } yield ()

      // OR better, not use fib directly and do: 
    import cats.syntax.parallel._
    cats.effect.std.Semaphore[cats.effect.IO].apply(2).flatMap(sem => (1 to 3).toList.parTraverse(login(_, sem)))
  }

  trait CountDownLatch[F[_]] {

    def apply(n : Int): IO[CountDownLatch[IO]] // Creates a CDLatch with n latches

    def await: IO[Unit] // Block the fiber until the count reaches 0

    def release: IO[Unit] // Release one latch 

      /*                                 The usual Workflow:                                 */
    import cats.syntax.parallel._
    def announcer(latch: cats.effect.std.CountDownLatch[cats.effect.IO]): cats.effect.IO[Unit] // Print countdown(5,4,3,2,1) and release 5 latches
    def runner(id: Int, latch: cats.effect.std.CountDownLatch[cats.effect.IO]): cats.effect.IO[Unit] // Print preparing and latch.wait

    for {
      latch <- cats.effect.std.CountDownLatch[cats.effect.IO].apply(5)
      announcerFib <- announcer(latch).start
      _ <- (1 to 10).toList.parTraverse(id => runner(id, latch))
      _ <- announcerFib.join
    } yield ()
  }

  trait CyclicBarrier[F[_]] {

    def apply(capacity: Int): IO[CyclicBarrier[IO]]

    def await: IO[Unit] // Block fiber calling it until we have exactly N fibers waiting then it will unblock all fibers
    
      /*                                 The usual Workflow:                                 */
    def createUser(id: Int, barrier: cats.effect.std.CyclicBarrier[cats.effect.IO]): cats.effect.IO[Unit]

    import cats.syntax.parallel._
    for {
      _ <- cats.effect.IO("Launching when we have 10 users!")
      barrier <- cats.effect.std.CyclicBarrier[cats.effect.IO](10)
      _ <- (1 to 20).toList.parTraverse(id => createUser(id, barrier))
    } yield ()




  }

  /*                                 BLOCK 5: POLYMORPHIC                                 */
  import cats.{Applicative, Monad}
  trait ApplicativeError[F[_], E] extends Applicative[F] {
    def raiseError[A](error: E): F[A]
    def handleErrorWith[A](fa: F[A])(f: E => F[A]): F[A]
  }
  trait MonadError[F[_], E] extends ApplicativeError[F, E] with Monad[F] 

  // Capabilities: pure, map/flatMap, raiseError, uncancelable, onCancel, gurantee, bracket
  trait MonadCancel[F[_], E] extends MonadError[F, E] {
    def canceled: F[Unit]

    import cats.effect.Poll
    def uncancelable[A](poll: Poll[F] => F[A]): F[A]
    
    def myPure[A](a: A): IO[A] // because it is a moand
    def map[A, B](fa: F[A])(f: A => B): F[B] // because it is a moand
    def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] // because it is a moand
  
    def onCancel[A](fin: F[Unit]): F[A]

    def guarantee[A](finalizer: F[Unit]): F[A] 

    def guaranteeCase[A](finalizer: cats.effect.kernel.Outcome[F, Throwable, A] => F[Unit]): F[A]

    def bracket[A, B](use: A => F[B])(release: A => F[Unit]): F[B] 

      /*                                 The usual Workflow:                                 */
    import cats.syntax.flatMap._ // flatMap: To be able to do fa.map(f) and not MonadCancel(fa)(f)
    import cats.syntax.functor._ // map: To be able to do fa.map(f) and not MonadCancel(fa)(f)
    import cats.effect.syntax.monadCancel._ // .onCancel, guarantee, guaranteeCase, bracket
    def unsafeSleep[F[_], E](duration: Long)(using mc: MonadCancel[F, E]): F[Unit] // We use an implicit for using it and we replace IO with mc


  }

  import cats.effect.Outcome
  trait GenSpawn[F[_], E] extends MonadCancel[F, E] {
    def start[A](fa: F[A]): F[Fiber[F, Throwable, A]] // creates a fiber
    def never[A]: F[A] // a forever-suspending effect
    def cede: F[Unit] // a "yield" effect

    def racePair[A, B](fa: F[A], fb: F[B]): F[Either[ // fundamental racing
      (Outcome[F, E, A], Fiber[F, E, B]),
      (Fiber[F, E, A], Outcome[F, E, B])
    ]]
  }
  // Capabilities: MonadCancel + start, never, cede, race
  trait Spawn[F[_]] extends GenSpawn[F, Throwable] {

    /*                                 The usual Workflow:                                 */
    import cats.syntax.flatMap._ // flatMap: To be able to do fa.map(f) and not MonadCancel(fa)(f)
    import cats.syntax.functor._ // map: To be able to do fa.map(f) and not MonadCancel(fa)(f)
    import cats.effect.syntax.spawn._ // start extension method
    def effectOnSomeThread[F[_], A](fa: F[A])(using spawn: Spawn[F]): F[Outcome[F, Throwable, A]] // Spawn is implicit and we replace the IO with spawn
  }

  // Capabilities: Spawn + ref, deferred,
  trait Concurrent[F[_]] extends Spawn[F] {
    def ref[A](a: A): F[Ref[F, A]]
    def deferred[A]: F[Deferred[F, A]]

    /*                                 The usual Workflow:                                 */
    import cats.syntax.flatMap._ // flatMap
    import cats.syntax.functor._ // map
    import cats.effect.syntax.spawn._ // start extension method if needed
    def polymorphicEggBoiler[F[_]](using concurrent: Concurrent[F]): F[Unit] // Concurrent is implicit and we replace IO with concurrent

  }

  // Capabilities: Concurrent + sleep
  trait Temporal[F[_]] extends Concurrent[F] {
    import scala.concurrent.duration.FiniteDuration
    def sleep(time: FiniteDuration): F[Unit] // semantically blocks this fiber for a specified time

/*                                 The usual Workflow:                                 */
    import cats.syntax.flatMap._ // flatMap
    import cats.syntax.functor._ // map
      import cats.effect.syntax.spawn._ // start extension method if needed
    def timeout[F[_], A](fa: F[A], duration: FiniteDuration)(using temporal: Temporal[F]): F[A] // Temporal is implicit and we replace IO with temporal
  }

  import cats.Defer
  // Capabilities: MonadCancel + delay/defer/blocking
  trait Sync[F[_]] extends MonadCancel[F, Throwable] with Defer[F] {
    def delay[A](thunk: => A): F[A] // "suspension" of a computation - will run on the CE thread pool

    def blocking[A](thunk: => A): F[A] // runs on the blocking thread pool

    def defer[A](thunk: => F[A]): F[A] = // defer comes for free
      flatMap(delay(thunk))(identity)

    /*                                 The usual Workflow:                                 */
    import cats.syntax.flatMap._ // flatMap
    import cats.syntax.functor._ // map
    def make[F[_]](using sync: Sync[F]): F[Int] // sync is implicit and we replace IO with sync
  }

  // Capabilities: Sync + Temporal + async, evalOn, executionContext
  trait Async[F[_]] extends Sync[F] with Temporal[F] {

    import scala.concurrent.ExecutionContext
    def executionContext: F[ExecutionContext]

    def async[A](cb: (Either[Throwable, A] => Unit) => F[Option[F[Unit]]]): F[A]

    def evalOn[A](fa: F[A], ec: ExecutionContext): F[A] // Shift the execution of the current IO to the ec

    def async_[A](cb: (Either[Throwable, A] => Unit) => Unit): F[A] // CE thread blocks until the cb is invoked in some other thread

    def never[A]: F[A] // If we do IO.async_[A](_ => ()) it will block forever because the cb will never be invoked

    /*                                 The usual Workflow:                                 */
    import cats.syntax.flatMap._ // flatMap
    import cats.syntax.functor._ // map
    def asyncComputation[F[_]](args: Int*)(using F: Async[F]): F[Int]
  }

}

object examplesCE {
  import cats.effect.{IO}

    /*                                        IOs                                              */
  import scala.io.StdIn
  def smallProgram(): IO[Unit] = for {
    line1 <- IO(StdIn.readLine())
    line2 <- IO(StdIn.readLine())
    _ <- IO(println(line1 + line2))
  } yield ()

  def smallProgram_v2(): IO[Unit] =
    import cats.syntax.apply._ // To use mapN
    (IO(StdIn.readLine()), IO(StdIn.readLine())).mapN(_ + _).map(println)

  def fibonacci(n: Int): IO[BigInt] =
    if (n < 2) IO(1)
    else for {
      last <- IO.defer(fibonacci(n - 1)) // same as .delay(...).flatten good for nested IO
      prev <- IO.defer(fibonacci(n - 2))
    } yield last + prev

  def fibonacciImproved(n: Int): IO[BigInt] = 
    def fibonacciImprovedTailrec(num: Int = n, acc1: BigInt = 1, acc2: BigInt = 1): IO[BigInt] = 
      if (num < 2) IO(acc1)
      else IO.defer(fibonacciImprovedTailrec(num - 1, acc1 + acc2, acc1))
    fibonacciImprovedTailrec()

  /*                                        PARALLELISM                                         */

  import scala.concurrent.duration._
  def sequentialProgram(): IO[String] = 
    val ioa: IO[Int] = IO.sleep(3.second).flatMap(_ => IO.pure(42))
    val iob: IO[String] = IO.sleep(5.second).flatMap(_ => IO.pure("Scala"))

    import cats.syntax.apply._ // To use mapN
    (ioa, iob).mapN((num, string) => s"Computed ioa with result $num and iob with result $string")

  def parallelProgram(): IO[String] = 
    val ioa: IO[Int] = IO.sleep(3.second).flatMap(_ => IO.pure(42))
    val iob: IO[String] = IO.sleep(5.second).flatMap(_ => IO.pure("Scala"))
    
    import cats.syntax.parallel._
    (ioa, iob).parMapN((num, string) => s"Computed ioa with result $num and iob with result $string")

  def sequentialProgram_2(): IO[Int] = 
    def computation(string: String): IO[Int] = IO.sleep(1.second).flatMap(_ => IO.pure(string.length()))
    val words: List[String] = List("Hello!", "How are you ?", "I Love", "Cats Effect!")

    import cats.syntax.traverse._
    words.traverse(string => computation(string)).flatMap(list => IO(list.sum))

  def parallelProgram_2(): IO[Int] = 
    def computation(string: String): IO[Int] = IO.sleep(1.second).flatMap(_ => IO.pure(string.length()))
    val words: List[String] = List("Hello!", "How are you ?", "I Love", "Cats Effect!")

    import cats.syntax.parallel._
    words.parTraverse(string => computation(string)).flatMap(list => IO(list.sum))

  /*                                        FIBERS                                         */
  /*                                        RACING                                         */
  /*                                      CANCELLING                                       */
  /*                                       BLOCKING                                        */
  /*                                        ASYNC                                          */
  /*                                      RESOURCES                                        */


  /*                                        REFS                                           */

  def programRef(): IO[Int] = {
    import cats.effect.Ref
    import utils.debug
    def task(workload: String, total: Ref[IO, Int]): IO[Unit] = 
      val wordCount = workload.split(" ").length

      for {
        _ <- IO(s"Counting words for '$workload': $wordCount'").debug
        newCount <- total.updateAndGet(currentCount => currentCount + wordCount)
        _ <- IO(s"New total: $newCount").debug
      } yield ()
    

    val list = List("I love Cats Effect", "This ref thing is very powerful", "I love typelevel")
    import cats.syntax.parallel._
    Ref[IO].of(0).flatMap {ref => 
      list.parTraverse(string => task(string, ref)) >> ref.get
    }

  }


  /*                                       DEFERS                                          */

  def programDeferred(): IO[String] = {
    val fileParts = List("I ", "love S", "cala", " with Cat", "s Effect!<EOF>")

    import cats.effect.{Ref, Deferred}
    import utils.debug
    def notifyFileComplete(signal: Deferred[IO, String]): IO[Unit] = for {
      _ <- IO("[notifier] downloading...").debug
      _ <- signal.get // blocks until the signal is completed
      _ <- IO("[notifier] File download complete").debug
    } yield ()

    def downloadFilePart(part: String, contentRef: Ref[IO, String], signal: Deferred[IO, String]): IO[Unit] = for {
      _ <- IO(s"[downloader] got '$part'").debug
      _ <- IO.sleep(1.second)
      latestContent <- contentRef.updateAndGet(currentContent => currentContent + part)
      _ <- if (latestContent.contains("<EOF>")) signal.complete(latestContent) else IO.unit
    } yield ()

    import cats.syntax.traverse._
    import cats.syntax.parallel._
    // THIS:
    // for {
    //   contentRef <- Ref[IO].of("")
    //   signal <- Deferred[IO, String]
    //   notifierFib <- notifyFileComplete(signal).start
    //   fileTasksFib <- fileParts.traverse(part => downloadFilePart(part, contentRef, signal)).start //.map(part => downloadFilePart(part, contentRef, signal)).sequence.start
    //   _ <- notifierFib.join
    //   _ <- fileTasksFib.join
    //   res <- contentRef.get
    // } yield res

    // OR: 
    Ref[IO].of("").flatMap {ref =>
      Deferred[IO, String].flatMap { signal =>
        (notifyFileComplete(signal), fileParts.traverse(part => downloadFilePart(part, ref, signal))).parTupled >> ref.get
      }  
    }
  }


  /*                                     SEMAPHORES                                        */

  def programSemaphore() = {
    import scala.util.Random
    def doComputationWhileLoggedIn(): IO[Int] = IO.sleep(1.second) >> IO(Random.nextInt(100))

    import cats.effect.std.Semaphore
    import utils.debug
    def login(id: Int, sem: Semaphore[IO]): IO[Int] = for {
      _ <- IO(s"[session $id] waiting to log in...").debug
      _ <- sem.acquire
      // critical section
      _ <- IO(s"[session $id] logged in, working...").debug
      res <- doComputationWhileLoggedIn()
      _ <- IO(s"[session $id] done: $res, logging out...").debug
      // end of critical section
      _ <- sem.release
    } yield res

    import cats.syntax.parallel._
    Semaphore[IO](2).flatMap(sem => (1 to 10).toList.parTraverse(login(_, sem)))
  } 


  /*                                  COUNTDOWNLATCHES                                     */
  /*                                   CYCLICBARRIERS                                      */

    
}

object mainCatsEffect extends App {
  import CatsEffect._
  import cats.effect.unsafe.implicits.global
  ???
}

import cats.effect.IOApp
object mainIOCatsEffect extends IOApp.Simple {
  import examplesCE._ 
  import utils._
  override def run: cats.effect.IO[Unit] = programRef().measure.void
}

object utils {
  import cats.effect.IO

  extension [A](io: IO[A])
    def debug: IO[A] = for {
      a <- io
      t = Thread.currentThread().getName
      _ = println(s"[$t] $a")
    } yield a

    def measure: IO[A] = for {
      startTime <- IO(System.currentTimeMillis())
      a <- io
      finishTime <- IO(System.currentTimeMillis())
      t = Thread.currentThread().getName
      _ = println(s"[$t] $a")
      _ = println(s"Time elapsed: ${finishTime - startTime}")
      _ = println( )
    } yield a

      def measureWithoutData: IO[A] = for {
      startTime <- IO(System.currentTimeMillis())
      a <- io
      finishTime <- IO(System.currentTimeMillis())
      _ = println(s"Time elapsed: ${finishTime - startTime}")
      _ = println( )
    } yield a

}