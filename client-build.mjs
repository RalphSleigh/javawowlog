import * as esbuild from 'esbuild'

let ctx = await esbuild.context({
  entryPoints: ['client/index.tsx'],
  bundle: true,
  outfile: 'web/bundle.js',
  sourcemap: true,
})

await ctx.watch()
console.log('Watching for changes...')