;; theres a box in room 1, and 2 boxes in room 2, move them all to room 3 and get shakey to room 1. 

(define (problem shakey_2)
  (:domain shakey)
  (:objects
   r-1 r-2 r-3 - room
   shakey-1 - shakey
   d-1w d-2 d-3w 
   - door
   l-1 l-2 l-3 - lightswitch
   b-1 b-2 b-3 - box
   )
  (:init  (at shakey-1 r-1)
	  (connecting d-1w r-1 r-2)(connecting d-1w r-2 r-1) ;; same as before
	  (connecting d-2 r-2 r-3)(connecting d-2 r-3 r-2)
	  (connecting d-3w r-2 r-3)(connecting d-3w r-3 r-2)
	  (wide d-1w)(wide d-3w)
	  (belong l-1, r-1)
	  (belong l-2, r-2)
	  (belong l-3, r-3)
	  (box_in b-1, r-1) ;; there's three boxes across two rooms
	  (box_in b-2, r-2)
	  (box_in b-3, r-2)


	  )
  (:goal (and(at shakey-1 r-1)
	     (box_in b-1 r-3)
	     (box_in b-2 r-3)
	     (box_in b-3 r-3)
	     

	     ))

  )
