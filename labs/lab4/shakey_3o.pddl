;; Shakey's World

(define (problem shakey1)
  (:domain shakeysworld)
  (:objects
      Room1 Room2 Room3 - room
      Shakey - shakey
      Door1 Door2 Door3 - door
      Toy1 Toy2 Toy3 Toy4 Toy5 - toy
      l1 l2 l3 - lightswitch
      Box1 Box2 Box3 - box
      ShakeysRightHand ShakeysLeftHand - hand
  )

  (:init
    (sin Shakey Room1)
    (iswide Door1) (iswide Door3)
    (lin l1 Room1) (lin l2 Room2) (lin l3 Room3)
    (bin Box1 Room1) (bin Box2 Room3) ;(bin Box3 Room3)
    (tin Toy1 Room2) (tin Toy2 Room2) (tin Toy3 Room2) (tin Toy4 Room2) (tin Toy5 Room3)
    (Doorway Door1 Room1 Room2) (Doorway Door1 Room2 Room1)
    (Doorway Door2 Room2 Room3) (Doorway Door2 Room3 Room2)
    (Doorway Door3 Room2 Room3) (Doorway Door3 Room3 Room2)
    ;(lon l1 Room1) (lon l2 Room2) (lon l3 Room3)
  )

  (:goal
    (and
    ;  (sin Shakey Room3)
      (not (lon l1 Room1)) (not (lon l2 Room2)) (not (lon l3 Room3))
      (tin Toy1 Room1) (tin Toy2 Room1) (tin Toy3 Room1) (tin Toy4 Room1) (tin Toy5 Room1)
    )
  )

)
