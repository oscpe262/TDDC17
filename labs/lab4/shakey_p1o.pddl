;; Shakey's World

; Simple functionality test, move Shakey from room1 to room3



(define (problem shakey1)
  (:domain shakeysworld)
  (:objects
      r1 r2 r3 - room
      Shakey - shakey
      d1 d2 d3 - door
      t1 - toy
      l1 l2 l3 - lightswitch
      b1 - box
  )

  (:init
    (sin Shakey r1)
    (iswide d1) (iswide d3)
    (lin l1 r1) (lin l2 r2) (lin l3 r3)
    (bin b1 r1) ;(bin b2 r2) (bin b3 r3)
    (tin t1 r1)
    (Doorway d1 r1 r2) (Doorway d1 r2 r1)
    (Doorway d2 r2 r3) (Doorway d2 r3 r2)
    (Doorway d3 r2 r3) (Doorway d3 r3 r2)
    (lon l2 r2)
  )

  (:goal
    (and
      (sin Shakey r3)
      (lon l1 r1)
      (not (lon l2 r2))
      (lon l3 r3)
    )
  )

)
