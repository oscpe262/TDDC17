;; gather exodia in room 1, then turn off the lights

(define (problem shakey_1)
  (:domain shakey)
  (:objects
   r-1 r-2 r-3 - room
   shakey-1 - shakey
   d-1w d-2 d-3w - door
   l-1 l-2 l-3 - lightswitch
   b-1 b-2 - box
   exodia exodia-leftarm exodia-rightarm exodia-leftleg exodia-rightleg - toy)
  (:init  (at shakey-1 r-1)
	  (connecting d-1w r-1 r-2)(connecting d-1w r-2 r-1) ;; same as before
	  (connecting d-2 r-2 r-3)(connecting d-2 r-3 r-2)
	  (connecting d-3w r-2 r-3)(connecting d-3w r-3 r-2)
	  (wide d-1w)(wide d-3w)
	  (belong l-1, r-1)
	  (belong l-2, r-2)
	  (belong l-3, r-3)
	  (in exodia r-2)             ;; 4 exodia pieces are placed in room 2, one is placed in room 3
	  (in exodia-leftarm r-2)
	  (in exodia-rightarm r-2)
	  (in exodia-leftleg r-2)
	  (in exodia-rightleg r-3)
	  (box_in b-1 r-1) ;; a box in room 1 and room 3
	  (box_in b-2 r-3)
	  )
  (:goal (and(at shakey-1 r-1)
	     (in exodia r-1)
	     (in exodia-leftarm r-1)
	     (in exodia-rightarm r-1)
	     (in exodia-leftleg r-1)
	     (in exodia-rightleg r-1)
	     (not (activated l-1))
	     (not (activated l-2))
	     (not (activated l-3))))

)
