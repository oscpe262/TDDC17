;; light all rooms and place shakey in room 1

(define (problem shakey_1)
  (:domain shakey)
  (:objects
   r-1 r-2 r-3 - room
   shakey-1 - shakey
   d-1w d-2 d-3w - door
   l-1 l-2 l-3 - lightswitch
   b-1 - box)
  (:init  (at shakey-1 r-1) ;; shakey is in room 1
	  (connecting d-1w r-1 r-2)(connecting d-1w r-2 r-1) ;; a wide door connects room 1 and 2
	  (connecting d-2 r-2 r-3)(connecting d-2 r-3 r-2) ;; a normal door connects room 2 and 3
	  (connecting d-3w r-2 r-3)(connecting d-3w r-3 r-2) ;; a wide door connects room 2 and 3 as well
	  (wide d-1w)(wide d-3w) ;; wide doors are wide
	  (belong l-1, r-1) ;;maps lightswitches to rooms
	  (belong l-2, r-2)
	  (belong l-3, r-3)
	  (box_in b-1, r-2) ;;there's a box in room 2
	  )
  (:goal (and(at shakey-1 r-1)(activated l-1)(activated l-2)(activated l-3)))

)
